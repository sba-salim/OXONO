package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import view.ConsoleView;
import view.MainWindow;
import view.SettingsPanel;

import java.util.Scanner;

import static view.GameAlert.showGameOverAlert;

public class Controller extends Application implements Observer {
    private boolean restarting = false;
    private Stage primaryStage;
    private Game game;
    private final Scanner scanner = new Scanner(System.in);
    private MainWindow mainWindow;
    private Position selectedTotem;


    @Override
    public void update(Game game) {
        if (restarting) return; // Ne rien faire pendant un redémarrage

        ConsoleView.update(game);
        if (mainWindow != null)
            mainWindow.updateView(game);

        if (game.isGameOver() && mainWindow != null) { // Vérifie si le jeu est terminé
            String winner = game.getCurrentPlayer().getC().toString();
            if (game.draw())
                winner = "NOBODY";
            showGameOverAlert(winner, this::restartGame, () -> System.exit(0));
        }
    }


    public void startConsole() {
        ConsoleView.displayWelcomeMessage();

        int size = promptBoardSize(); // Lire la taille du plateau
        boolean singlePlayer = promptGameMode(); // Lire le mode de jeu

        // Initialiser le jeu
        this.game = new Game(size, singlePlayer);
        game.registerObserver(this);

        // Boucle principale du jeu
        while (!game.isGameOver()) {
            try {
                System.out.print("> ");
                String input = scanner.nextLine();
                parseCommand(input); // Analyse et exécute la commande
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private int promptBoardSize() {
        int size;
        while (true) {
            try {
                System.out.print("Enter the board size (minimum 4): ");
                size = Integer.parseInt(scanner.nextLine());
                if (size >= 4 && size % 2 == 0) {
                    return size;
                }
                System.out.println("The size must be at least 4 and pair.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    private boolean promptGameMode() {
        while (true) {
            System.out.print("Single player or two players? (Enter '1' for single player, '2' for two players): ");
            String input = scanner.nextLine();
            if (input.equals("1")) {
                return true; // Mode un joueur
            } else if (input.equals("2")) {
                return false; // Mode deux joueurs
            } else {
                System.out.println("Invalid input. Please enter '1' or '2'.");
            }
        }
    }

    private void parseCommand(String input) {
        if (input.matches("move \\d+ \\d+ [xo]")) {
            handleMove(input);
        } else if (input.matches("insert \\d+ \\d+")) {
            handleInsert(input);
        } else if (input.equals("quit")) {
            handleQuit();
        } else if (input.matches("help")) {
            ConsoleView.displayHelp();
        } else if (input.matches("undo")) {
            game.undo();
        } else if (input.matches("redo")) {
            game.redo();
        } else {
            System.out.println("Unknown command! Type 'help' for a list of commands.");
        }
    }

    private void handleMove(String input) {
        String[] parts = input.split("\\s+");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        Symbol symbol = Symbol.valueOf(parts[3].toUpperCase());

        if (game.moveTotem(new Position(x, y), symbol)) {
            System.out.println("Totem moved successfully.");
        } else {
            System.out.println("Invalid totem move.");
        }
    }

    private void handleInsert(String input) {
        String[] parts = input.split("\\s+");
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);

        if (game.insertPawn(new Position(x, y))) {
            System.out.println("Pawn inserted successfully.");
        } else {
            System.out.println("Invalid pawn insertion.");
        }
    }

    public void restartGame() {
        try {
            restarting = true;
            game.removeObserver(this);

            if (primaryStage != null) {
                primaryStage.close(); // Ferme la fenêtre actuelle
            }

            primaryStage = new Stage(); // Crée une nouvelle fenêtre
            game = new Game(/* taille ou paramètres */); // Nouvelle instance du jeu
            game.registerObserver(this); // Réenregistrer le contrôleur

            start(primaryStage); // Relance l'application

            restarting = false; // Terminé après la configuration
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }


    public void handleQuit() {
        game.surrender();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.startConsole();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        // Fenêtre de configuration initiale
        SettingsPanel settingsPanel = new SettingsPanel(this);
        settingsPanel.show(primaryStage);
    }

    public void setupGame(int size, boolean singlePlayer, Stage primaryStage) {
        this.game = new Game(size, singlePlayer);
        mainWindow = new MainWindow(primaryStage, this);
        mainWindow.drawBoard(size);
        game.registerObserver(this);
    }


    public void handleCellClick(int x, int y) {
        if (game.hasToMove() && selectedTotem != null) {
            boolean success = game.moveTotem(new Position(x, y), game.getTokenAt(selectedTotem).getS());
            if (success) {
                mainWindow.getStatusPanel().updateMessage("Totem moved to (" + x + ", " + y + ").");
            } else {
                mainWindow.getStatusPanel().updateMessage("Invalid move. Try again.");
            }
            selectedTotem = null; // Réinitialise la sélection
        } else if (game.hasToMove() && game.getTokenAt(new Position(x, y)) instanceof Totem) {
            selectedTotem = new Position(x, y);
        } else if (!game.hasToMove())
            game.insertPawn(new Position(x, y));
    }
}
