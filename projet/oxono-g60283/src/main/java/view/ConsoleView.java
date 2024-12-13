package view;

import model.Game;
import model.Position;
import model.Token;

public class ConsoleView {

    public static void update(Game game) {
        displayBoard(game);
    }
    public static void displayBoard(Game game) {
        System.out.println("Current Board:");
        for (int x = 0; x < game.getSize(); x++) {
            for (int y = 0; y < game.getSize(); y++) {
                Token token = game.getTokenAt(new Position(x, y));
                switch (token) {
                    case null -> System.out.print(" . "); // Case vide
                    case model.Totem totem -> System.out.print(token.getS() + "T "); // Totem
                    case model.Pawn pawn -> System.out.print(token.getS() + "P "); // Pawn
                    default -> {
                    }
                }
            }
            System.out.println();
        }
        System.out.println("Current player : " + game.getCurentPlayersColor());
    }
    public static void displayWelcomeMessage() {
        System.out.println("Welcome to the OXONO Game!");
        System.out.println("Align 4 pawns of the same color or symbol to win!");
        System.out.println("Type 'help' for a list of commands.");
    }
    public static void displayGameOver() {

    }
    public static void displayHelp() {
        System.out.println("Available commands:");
        System.out.println("- move x y [X|O]: Move a totem to position (x, y) with symbol X or O.");
        System.out.println("- insert x y: Insert a pawn at position (x, y).");
        System.out.println("- restart: Restart the game.");
        System.out.println("- quit: Exit the game.");
        System.out.println("- help: Show this help message.");
    }
}
