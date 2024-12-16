package model;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for validating the positions available for moves and pawn insertions on the game board.
 * This class provides methods to determine valid positions for a Totem to move and valid positions
 * to insert new pawns, based on the current state of the board.
 */
public class PositionsValidator {

    /**
     * Calculates the valid positions for a Totem's movement based on the current position of the Totem,
     * and the state of the board.
     *
     * @param s the symbol (X or O) of the Totem.
     * @param b the game board instance where the Totem resides.
     * @return a list of valid positions where the Totem can move.
     */
    public static List<Position> movePositions(Symbol s, Board b) {
        Position totemPos = b.getTotemPos(s);  // Get the current position of the Totem
        System.out.println("Totempos = " + totemPos);
        List<Position> validPositions = new ArrayList<>();

        // If the Totem is not enclosed, search for available positions in the same row and column
        if (!b.isEnclaved(totemPos)) {
            System.out.println("Totem is not enclaved.");

            // Checking valid positions in the same row
            for (int col = 0; col < b.getSize(); col++) {
                Position candidate = new Position(totemPos.x(), col);
                if (b.isEmpty(candidate) && b.isPathClear(candidate, s)) {
                    validPositions.add(candidate);
                }
            }

            // Checking valid positions in the same column
            for (int row = 0; row < b.getSize(); row++) {
                Position candidate = new Position(row, totemPos.y());
                if (b.isEmpty(candidate) && b.isPathClear(candidate, s)) {
                    validPositions.add(candidate);
                }
            }

            System.out.println(validPositions);
            return validPositions;
        }

        // If the Totem is enclosed, check for available positions in the row and column with the path full
        for (int col = 0; col < b.getSize(); col++) {
            Position candidate = new Position(totemPos.x(), col);
            if (b.isEmpty(candidate) && b.isPathFull(candidate, totemPos)) {
                validPositions.add(candidate);
            }
        }

        for (int row = 0; row < b.getSize(); row++) {
            Position candidate = new Position(row, totemPos.y());
            if (b.isEmpty(candidate) && b.isPathFull(candidate, totemPos)) {
                validPositions.add(candidate);
            }
        }

        // If no valid positions were found, return all empty positions on the board
        if (!validPositions.isEmpty())
            return validPositions;

        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                Position candidate = new Position(i, j);
                if (b.isEmpty(candidate)) {
                    validPositions.add(candidate);
                }
            }
        }

        return validPositions;
    }

    /**
     * Calculates the valid positions where pawns can be inserted on the board.
     * It first checks for valid positions around the Totem, and if none are found,
     * it returns all empty positions on the board.
     *
     * @param b the game board instance.
     * @return a list of valid positions where pawns can be inserted.
     */
    public static List<Position> insertPositions(Board b) {
        List<Position> validPositions = getEmptyNeighbors(b, b.getTotemPos(b.getLastTouchedSymbol()));

        if (!validPositions.isEmpty()) {
            return validPositions;
        }

        // If no valid neighboring positions found, return all empty positions on the board
        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                Position candidate = new Position(i, j);
                if (b.isEmpty(candidate)) {
                    validPositions.add(candidate);
                }
            }
        }
        return validPositions;
    }

    /**
     * Returns a list of valid neighboring positions around a given position where a pawn can be placed.
     *
     * @param b the game board instance.
     * @param p the current position to check neighboring positions for.
     * @return a list of neighboring empty positions where a pawn can be placed.
     */
    private static List<Position> getEmptyNeighbors(Board b, Position p) {
        List<Position> emptyNeighbors = new ArrayList<>();

        // Directions: up, down, left, right
        int[][] directions = {
                {-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1}   // Right
        };

        // Check each direction for an empty neighbor position
        for (int[] dir : directions) {
            int newX = p.x() + dir[0];
            int newY = p.y() + dir[1];
            Position neighbor = new Position(newX, newY);

            // Check if the neighbor is inside the board and empty
            if (b.isInside(neighbor) && b.isEmpty(neighbor)) {
                emptyNeighbors.add(neighbor);
            }
        }

        return emptyNeighbors;
    }
}
