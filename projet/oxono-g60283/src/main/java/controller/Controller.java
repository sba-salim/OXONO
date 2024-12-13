package controller;

import model.*;
import view.ConsoleView;

import java.util.Scanner;

public class Controller implements Observer {
    private Game game;
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void update(Game game) {
        ConsoleView.update(game);
    }

    public void start() {
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
                if (size >= 4 && size%2==0) {
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

    private void handleQuit() {
        game.surrender();
    }

    public static void main(String[] args) {
        Controller c = new Controller();
        c.start();
    }
}
