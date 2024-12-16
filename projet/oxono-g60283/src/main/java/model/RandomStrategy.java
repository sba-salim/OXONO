package model;

import model.strategy.Strategy;

import java.util.List;
import java.util.Random;

/**
 * RandomStrategy implements a random move or insertion strategy for a player.
 * It chooses a symbol intelligently based on the available pawns of the player,
 * and then randomly selects a valid move or insertion position on the board.
 */
public class RandomStrategy implements Strategy {
    private final Game game;
    private final Player player;
    private final Board board;

    /**
     * Constructs a RandomStrategy with the given game, player, and board.
     *
     * @param game the game instance to interact with the game logic.
     * @param player the player who will perform the random strategy.
     * @param board the game board where the moves and insertions will occur.
     */
    public RandomStrategy(Game game, Player player, Board board) {
        this.game = game;
        this.player = player;
        this.board = board;
    }

    /**
     * Executes the random strategy. The strategy randomly chooses either to:
     * 1. Move the totem to a valid position.
     * 2. Insert a pawn at a valid position.
     * It checks the available pawns and moves before making a decision.
     */
    @Override
    public void execute() {
        Random random = new Random();

        // Determine the symbol to use for the action (X or O)
        Symbol symbolToUse = selectSymbol(random);

        // If no pawns are available for either symbol, exit the method
        if (symbolToUse == null) {
            System.out.println("Player has no pawns to move or insert.");
            return;
        }

        // Check if the player has to move the Totem
        if (game.hasToMove()) {
            // Find the valid move positions for the Totem
            List<Position> validMovePositions = PositionsValidator.movePositions(symbolToUse, board);
            System.out.println("movepos : " + validMovePositions);
            if (!validMovePositions.isEmpty()) {
                // Randomly choose a position to move the Totem to
                Position chosenPosition = validMovePositions.get(random.nextInt(validMovePositions.size()));
                game.moveTotem(chosenPosition, symbolToUse);
            } else {
                System.out.println("No valid moves available for the totem.");
            }
        }

        // Find the valid positions for inserting a pawn
        List<Position> validInsertPositions = PositionsValidator.insertPositions(board);
        System.out.println("insertpos" + validInsertPositions);
        if (!validInsertPositions.isEmpty()) {
            // Randomly choose a position to insert a pawn
            Position chosenPosition = validInsertPositions.get(random.nextInt(validInsertPositions.size()));
            game.insertPawn(chosenPosition);
        } else {
            System.out.println("No valid positions available to insert a pawn.");
        }
    }

    /**
     * Selects a symbol to use (either X or O) based on the available pawns of the player.
     * If the player has both types of pawns, one is chosen randomly.
     *
     * @param random Random object to make a random choice if needed.
     * @return the chosen symbol (X or O), or null if the player has no pawns.
     */
    private Symbol selectSymbol(Random random) {
        boolean hasX = player.hasPawn(Symbol.X);
        boolean hasO = player.hasPawn(Symbol.O);

        if (hasX && hasO) {
            // If the player has both X and O, randomly choose one
            return random.nextBoolean() ? Symbol.X : Symbol.O;
        } else if (hasX) {
            return Symbol.X;
        } else if (hasO) {
            return Symbol.O;
        }

        // No pawns available
        return null;
    }
}
