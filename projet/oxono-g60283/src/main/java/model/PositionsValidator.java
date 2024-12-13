package model;

import java.util.ArrayList;
import java.util.List;

public class PositionsValidator {
    public static List<Position> movePositions(Symbol s, Board b) {
        Position totemPos = b.getTotemPos(s);
        System.out.println("Totempos = " + totemPos);
        List<Position> validPositions = new ArrayList<>();
        if (!b.isEnclaved(totemPos)) {
            System.out.println("Totem is not enclaved.");

            for (int col = 0; col < b.getSize(); col++) { // Supposons une grille 6x6
                Position candidate = new Position(totemPos.x(), col);
                if (b.isEmpty(candidate) && b.isPathClear(candidate, s)) {
                    validPositions.add(candidate);
                }
            }
            System.out.println(validPositions);

            // Vérifier les positions dans la même colonne
            for (int row = 0; row < b.getSize(); row++) { // Supposons une grille 6x6
                Position candidate = new Position(row, totemPos.y());
                if (b.isEmpty(candidate) && b.isPathClear(candidate, s)) {
                    validPositions.add(candidate);
                }
            }
            System.out.println(validPositions);
            return validPositions;
        }
        for (int col = 0; col < b.getSize(); col++) { // Supposons une grille 6x6
            Position candidate = new Position(totemPos.x(), col);
            if (b.isEmpty(candidate) && b.isPathFull(candidate, totemPos)) {
                validPositions.add(candidate);
            }
        }
        for (int row = 0; row < b.getSize(); row++) { // Supposons une grille 6x6
            Position candidate = new Position(row, totemPos.y());
            if (b.isEmpty(candidate) && b.isPathFull(candidate, totemPos)) {
                validPositions.add(candidate);
            }
        }
        if (!validPositions.isEmpty())
            return validPositions;
        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                Position candidate = new Position(i, j);
                if (b.isEmpty(candidate))
                    validPositions.add(candidate);
            }

        }
        return validPositions;
    }

    public static List<Position> insertPositions(Board b) {
        List<Position> validPositions;
        validPositions = getEmptyNeighbors(b, b.getTotemPos(b.getLastTouchedSymbol()));

        if (!validPositions.isEmpty())
            return validPositions;
        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                Position candidate = new Position(i, j);
                if (b.isEmpty(candidate))
                    validPositions.add(candidate);
            }
        }
        return validPositions;
    }

    private static List<Position> getEmptyNeighbors(Board b, Position p) {
        List<Position> emptyNeighbors = new ArrayList<>();

        // Directions: up, down, left, right
        int[][] directions = {
                {-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1}   // Right
        };

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





