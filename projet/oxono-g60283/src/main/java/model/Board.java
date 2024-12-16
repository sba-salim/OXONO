package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The `Board` class represents the game board. It manages the grid of tokens and the positions of the totems and pawns.
 * It also provides various methods for checking the validity of moves, insertions, and determining if the game is won.
 */
public class Board {
    private final Token[][] grid;
    private Position PosX;
    private Position PosO;
    private Position LastTouched;

    /**
     * Default constructor to initialize a 6x6 board and place the totems randomly.
     * The totems are placed at the center of the board, either at (2,2) for X and (3,3) for O or vice versa.
     */
    public Board() {
        this.grid = new Token[6][6];
        boolean placement = getRandomBool();
        if (placement) {
            this.PosX = new Position(2, 2);
            grid[2][2] = new Totem(Symbol.X);
            this.PosO = new Position(3, 3);
            grid[3][3] = new Totem(Symbol.O);
        } else {
            this.PosX = new Position(3, 3);
            grid[3][3] = new Totem(Symbol.X);
            this.PosO = new Position(2, 2);
            grid[2][2] = new Totem(Symbol.O);
        }
    }

    /**
     * Constructor to initialize a board of the specified size.
     * The size must be an even number greater than or equal to 4. The totems are placed in the center of the board.
     *
     * @param size The size of the board (must be even and >= 4).
     */
    public Board(int size) {
        if (size < 4 || size % 2 != 0)
            throw new IllegalStateException("size must be even and greater than four.");
        this.grid = new Token[size][size];
        boolean placement = getRandomBool();
        if (placement) {
            this.PosX = new Position(size / 2 - 1, size / 2 - 1);
            grid[size / 2 - 1][size / 2 - 1] = new Totem(Symbol.X);
            this.PosO = new Position(size / 2, size / 2);
            grid[size / 2][size / 2] = new Totem(Symbol.O);
        } else {
            this.PosX = new Position(size / 2, size / 2);
            grid[size / 2][size / 2] = new Totem(Symbol.X);
            this.PosO = new Position(size / 2 - 1, size / 2 - 1);
            grid[size / 2 - 1][size / 2 - 1] = new Totem(Symbol.O);
        }
    }

    /**
     * Removes a pawn from the board at the given position.
     *
     * @param p The position of the pawn to remove.
     */
    public void removePawn(Position p) {
        grid[p.x()][p.y()] = null;
    }

    /**
     * Checks if a position is inside the board's grid.
     *
     * @param p The position to check.
     * @return true if the position is inside the grid, false otherwise.
     */
    public boolean isInside(Position p) {
        return p.x() >= 0 && p.x() < grid.length && p.y() >= 0 && p.y() < grid[0].length;
    }

    /**
     * Checks if the position is empty (no token present).
     *
     * @param p The position to check.
     * @return true if the position is empty, false otherwise.
     */
    boolean isEmpty(Position p) {
        return grid[p.x()][p.y()] == null;
    }

    /**
     * Generates a random boolean value.
     *
     * @return A random boolean value.
     */
    private static boolean getRandomBool() {
        Random r = new Random();
        return r.nextBoolean();
    }

    /**
     * Returns the list of neighboring positions around a given position on the board.
     *
     * @param position The position whose neighbors are to be found.
     * @return A list of neighboring positions.
     */
    List<Position> getNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        if (!isInside(position))
            return neighbors;

        // Directions of all possible neighbors
        int[][] directions = {{-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1}}; // Right

        for (int[] direction : directions) {
            Position neighborPos = new Position(position.x() + direction[0], position.y() + direction[1]);
            if (!isInside(neighborPos)) {
                neighbors.add(null);
            } else if (!isEmpty(neighborPos)) {
                neighbors.add(neighborPos); // Add the token if it exists
            }
        }
        return neighbors;
    }

    /**
     * Inserts a pawn into the specified position on the board.
     *
     * @param pos The position where the pawn should be inserted.
     * @param pawn The pawn to insert.
     * @return true if the insertion was successful, false otherwise.
     */
    public boolean insertPawn(Position pos, Pawn pawn) {
        if (isValidInsert(pos, pawn.getS())) {
            grid[pos.x()][pos.y()] = pawn;
            return true;
        } else if (isEnclaved(new Position(LastTouched.x(), LastTouched.y()))
                && isEmpty(pos)
                && grid[LastTouched.x()][LastTouched.y()].getS() == pawn.getS()) {
            grid[pos.x()][pos.y()] = pawn;
            return true;
        }
        return false;
    }

    /**
     * Checks if the insertion of a pawn at a specified position is valid.
     *
     * @param p The position where the pawn is to be inserted.
     * @param s The symbol of the pawn to insert.
     * @return true if the insertion is valid, false otherwise.
     */
    boolean isValidInsert(Position p, Symbol s) {
        List<Position> neighbours = getNeighbors(p);

        if (!isEmpty(p) || !isInside(p)) return false;
        if (grid[LastTouched.x()][LastTouched.y()].getS() != s) // Ensures that the last moved totem has the same symbol as the pawn
            return false;
        else if (s == Symbol.X) {
            return neighbours.contains(PosX);
        } else if (s == Symbol.O) {
            return neighbours.contains(PosO);
        }
        return false;
    }

    /**
     * Checks if a given position is surrounded by out-of-bounds positions or tokens.
     *
     * @param p The position to check.
     * @return true if the position is enclosed, false otherwise.
     */
    boolean isEnclaved(Position p) {
        return getNeighbors(p).size() == 4;
    }

    /**
     * Checks if two positions are not aligned either vertically or horizontally.
     *
     * @param p1 The first position.
     * @param p2 The second position.
     * @return true if the positions are not aligned, false otherwise.
     */
    boolean isNotAligned(Position p1, Position p2) {
        return p1.x() != p2.x() && p1.y() != p2.y() || p1.x() == p2.x() && p1.y() == p2.y();
    }

    /**
     * Checks if the path between a position and the totem is clear (no tokens blocking the path).
     *
     * @param p The position to check.
     * @param s The symbol of the totem to check (X or O).
     * @return true if the path is clear, false otherwise.
     */
    public boolean isPathClear(Position p, Symbol s) {
        Position totemPos = s == Symbol.X ? PosX : PosO;

        if (isNotAligned(p, totemPos)) {
            return false; // Not aligned or in the same position
        }

        int stepX = Integer.signum(totemPos.x() - p.x());
        int stepY = Integer.signum(totemPos.y() - p.y());

        Position current = p;

        while (!current.equals(totemPos)) {
            if (!isEmpty(current)) {
                return false;
            }
            current = new Position(current.x() + stepX, current.y() + stepY);
        }

        return true;
    }

    /**
     * Checks if the totem can be moved to a given position.
     *
     * @param p The position to move the totem to.
     * @param s The symbol of the totem to move.
     * @return true if the totem can be moved, false otherwise.
     */
    public boolean moveTotem(Position p, Symbol s) {
        Position totemPos = s == Symbol.X ? PosX : PosO;
        if ((isInside(p) && isPathClear(p, s))
                || (isEnclaved(totemPos) && isPathFull(p, totemPos))) {
            grid[p.x()][p.y()] = grid[totemPos.x()][totemPos.y()];
            grid[totemPos.x()][totemPos.y()] = null;
            LastTouched = p;
            if (s == Symbol.X)
                PosX = p;
            else PosO = p;
            return true;
        }
        return false;
    }

    /**
     * Checks if the path between the totem's current position and a given position is fully occupied.
     *
     * @param p The position to check.
     * @param totemPos The current position of the totem.
     * @return true if the path is full, false otherwise.
     */
    boolean isPathFull(Position p, Position totemPos) {
        if (isNotAligned(p, totemPos))
            return false;

        int stepX = Integer.signum(p.x() - totemPos.x());
        int stepY = Integer.signum(p.y() - totemPos.y());
        Position current = totemPos;

        while (!current.equals(p)) {
            if (isEmpty(current)) {
                return false;
            }
            current = new Position(current.x() + stepX, current.y() + stepY);
        }
        return true;
    }
    /**
     * Checks if there is a winning line (horizontally or vertically) from the given position.
     * This method checks if there are at least 4 consecutive pawns of the same symbol or color.
     *
     * @param pos The position to check for a winning line.
     * @return true if a winning line is found, false otherwise.
     */
    boolean checkLine(Position pos) {
        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return false; // Ensure position contains a Pawn

        Symbol symbol = t.getS();
        Color color = ((Pawn) t).getC();

        // Check left and right directions for symbol and color
        return countConsecutive(pos, symbol, true) >= 4 ||
                countConsecutive(pos, color, true) >= 4;
    }

    /**
     * Checks if there is a winning column (vertically) from the given position.
     * This method checks if there are at least 4 consecutive pawns of the same symbol or color.
     *
     * @param pos The position to check for a winning column.
     * @return true if a winning column is found, false otherwise.
     */
    boolean checkColumn(Position pos) {
        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return false; // Ensure position contains a Pawn

        Symbol symbol = t.getS();
        Color color = ((Pawn) t).getC();

        // Check up and down directions for symbol and color
        return countConsecutive(pos, symbol, false) >= 4 ||
                countConsecutive(pos, color, false) >= 4;
    }

    /**
     * Counts consecutive pawns of the same symbol or color in a given direction (horizontal or vertical).
     * It checks both directions (left/right or up/down) from the given position for consecutive pawns.
     *
     * @param pos The position from which to start the count.
     * @param attribute The attribute to match (either a symbol or color of the pawn).
     * @param horizontal A flag indicating whether to check horizontally (true) or vertically (false).
     * @return The number of consecutive pawns with the same attribute in the specified direction.
     */
    int countConsecutive(Position pos, Object attribute, boolean horizontal) {
        int count = 1; // Include the initial position
        int x = pos.x();
        int y = pos.y();

        // Check left/up
        for (int i = 1; i < 4; i++) {
            int newX = horizontal ? x : x - i;
            int newY = horizontal ? y - i : y;
            if (!isInside(new Position(newX, newY)) || doesNotMatchAttribute(new Position(newX, newY), attribute)) {
                break;
            }
            count++;
        }

        // Check right/down
        for (int i = 1; i < 4; i++) {
            int newX = horizontal ? x : x + i;
            int newY = horizontal ? y + i : y;
            if (!isInside(new Position(newX, newY)) || doesNotMatchAttribute(new Position(newX, newY), attribute)) {
                break;
            }
            count++;
        }

        return count;
    }

    /**
     * Checks if a given position does not match the provided attribute (either symbol or color).
     *
     * @param pos The position to check.
     * @param attribute The attribute to match (either a symbol or color).
     * @return true if the position does not match the attribute, false if it matches.
     */
    public boolean doesNotMatchAttribute(Position pos, Object attribute) {
        if (!isInside(pos)) return true;

        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return true;

        if (attribute instanceof Symbol) {
            return t.getS() != attribute;
        } else if (attribute instanceof Color) {
            return ((Pawn) t).getC() != attribute;
        }
        return true;
    }

    /**
     * Checks if a position results in a win condition by checking its line (horizontal/vertical).
     *
     * @param pos The position to check.
     * @return true if a win condition is met, false otherwise.
     */
    boolean checkWin(Position pos) {
        return checkLine(pos) || checkColumn(pos);
    }

    /**
     * Returns the symbol of the last touched token.
     *
     * @return The symbol of the last touched token.
     */
    public Symbol getLastTouchedSymbol() {
        return grid[LastTouched.x()][LastTouched.y()].getS();
    }

    /**
     * Returns the position of the totem based on its symbol (X or O).
     *
     * @param s The symbol of the totem (X or O).
     * @return The position of the totem.
     */
    public Position getTotemPos(Symbol s) {
        return s == Symbol.X ? new Position(PosX.x(), PosO.y()) : new Position(PosO.x(), PosO.y());
    }

    /**
     * Returns the token at a given position on the board.
     *
     * @param p The position to check.
     * @return The token at the given position.
     */
    public Token getTokenAt(Position p) {
        return grid[p.x()][p.y()];
    }

    /**
     * Returns the size of the board (number of rows/columns).
     *
     * @return The size of the board.
     */
    public int getSize() {
        return grid.length;
    }
}
