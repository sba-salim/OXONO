package controller;

import model.Game;
import model.Observer;
import model.Position;
import model.Symbol;
import view.ConsoleView;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller implements Observer {
    private final Game game;

    public Controller() {
        this.game = new Game();
        game.registerObserver(this);
    }

    @Override
    public void update(Game game) {
        ConsoleView.update(game);
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
        } else {
            System.out.println("Unknown command! Type 'help' for a list of commands.");
        }
    }
    public void start() {
        ConsoleView.displayWelcomeMessage();

        while (!game.isGameOver()) {
            try {
                System.out.print("> ");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                parseCommand(input); // Analyse et exécute la commande
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }

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


    // Méthode pour traiter une commande d'insertion de pion
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
