package model;

import model.strategy.Strategy;

import java.util.List;
import java.util.Random;

/**
 * RandomStrategy implements a random move or insertion for the player.
 * It selects a symbol intelligently based on the player's available pawns.
 */
public class RandomStrategy implements Strategy {
    private final Game game;
    private final Player player;
    private final Board board;

    public RandomStrategy(Game game, Player player, Board board) {
        this.game = game;
        this.player = player;
        this.board = board;
    }

    @Override
    public void execute() {
        Random random = new Random();

        // Determine the symbol to use
        Symbol symbolToUse = selectSymbol(random);

        if (symbolToUse == null) {
            System.out.println("Player has no pawns to move or insert.");
            return;
        }

        if (game.hasToMove()) {
            // Move the totem
            List<Position> validMovePositions = PositionsValidator.movePositions(symbolToUse, board);

            if (!validMovePositions.isEmpty()) {
                Position chosenPosition = validMovePositions.get(random.nextInt(validMovePositions.size()));
                game.moveTotem(chosenPosition, symbolToUse);
            } else {
                System.out.println("No valid moves available for the totem.");
            }
        }
        // Insert a pawn
        List<Position> validInsertPositions = PositionsValidator.insertPositions(board);
        System.out.println(validInsertPositions);
        if (!validInsertPositions.isEmpty()) {
            Position chosenPosition = validInsertPositions.get(random.nextInt(validInsertPositions.size()));
            game.insertPawn(chosenPosition);
        } else {
            System.out.println("No valid positions available to insert a pawn.");
        }

    }

    /**
     * Selects a symbol to use for the action based on the player's available pawns.
     *
     * @param random Random object to make random choices.
     * @return The chosen symbol, or null if the player has no pawns.
     */
    private Symbol selectSymbol(Random random) {
        boolean hasX = player.hasPawn(Symbol.X);
        boolean hasO = player.hasPawn(Symbol.O);

        if (hasX && hasO) {
            // Choose a random symbol if the player has both
            return random.nextBoolean() ? Symbol.X : Symbol.O;
        } else if (hasX) {
            return Symbol.X;
        } else if (hasO) {
            return Symbol.O;
        }

        // No symbols available
        return null;
    }
}
