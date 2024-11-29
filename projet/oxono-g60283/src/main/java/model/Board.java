package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final Token[][] grid;
    private Position PosX;
    private Position PosO;
    private Position LastTouched;

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


    private boolean isInside(Position p) {
        return p.x() >= 0 && p.x() < grid.length && p.y() >= 0 && p.y() < grid[0].length;
    }

    boolean isEmpty(Position p) {
        //todo: Gérer les positions en dehors du grid
        return grid[p.x()][p.y()] == null;
    }

    private static boolean getRandomBool() {
        Random r = new Random();
        return r.nextBoolean();//Génére un booleen aléatoire
    }

    private List<Position> getNeighbors(Position position) {
        List<Position> neighbors = new ArrayList<>();
        // Directions of all possible neighbors
        int[][] directions = {{-1, 0}, // Up
                {1, 0},  // Down
                {0, -1}, // Left
                {0, 1}   // Right
        };
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

    //todo corriger les param
    void insertPawn(Position pos, Pawn pawn) {
        if (isValidInsert(pos,pawn.getS()))
            grid[pos.x()][pos.y()]= pawn;
    }

    boolean isValidInsert(Position p, Symbol s) {
        List<Position> neighbours = getNeighbors(p);

        if (!isEmpty(p) || !isInside(p)) return false;
        if (grid[LastTouched.x()][LastTouched.y()].getS() != s)//Checks that the last totem moved has the same symbol as the pawn inserted
            return false;
        else if (s == Symbol.X) {
            return neighbours.contains(PosX);
        } else if (s == Symbol.O) {
            return neighbours.contains(PosO);
        }
        return false;

    }

    boolean isEnclaved(Position p) {
        //Make sure that the totem is surrounded by out-of-bounds positions or by tokens
        return getNeighbors(p).size() == 4;
    }

    boolean isNotAligned(Position p1, Position p2) {
        return p1.x() != p2.x() && p1.y() != p2.y() || p1.x() == p2.x() && p1.y() == p2.y();
    }

    public boolean isPathClear(Position p, Symbol s) {
        Position totemPos = s == Symbol.X ? PosX : PosO;

        // Assuring that the positions are either aligned vertically or horizontally and that they're not the same position
        if (isNotAligned(p, totemPos)) {
            return false; // Not aligned or in the same position
        }
        // Initialization of the "direction"
        int stepX = Integer.signum(totemPos.x() - p.x()); // Direction in x
        int stepY = Integer.signum(totemPos.y() - p.y()); // Direction in y

        Position current = p;

        // Checking all positions except totemPos
        while (!current.equals(totemPos)) {
            if (!isEmpty(current)) {
                return false; // A slot is occupied/not empty
            }
            // Incrementing in the right direction
            current = new Position(current.x() + stepX, current.y() + stepY);
        }

        return true; // The path is clear
    }

    /*boolean isVa  lidMove(Position p, Symbol s) {
        return isPathClear(p, s);
    }
    */
    void moveTotem(Position p, Symbol s) {
        Position totemPos = s == Symbol.X ? PosX : PosO;
        if (isInside(p) && isPathClear(p, s) || isEnclaved(totemPos) && isPathFull(p, totemPos)) {
            grid[p.x()][p.y()] = grid[totemPos.x()][totemPos.y()];
            grid[totemPos.x()][totemPos.y()] = null;
            LastTouched = p;
            if (s == Symbol.X)
                PosX = p;
            else PosO = p;
        }
    }

    boolean isPathFull(Position p, Position totemPos) {//Checks there is no empty slot between the totem and p
        if (isNotAligned(p, totemPos))
            return false;
        // Initialization of the "direction"
        int stepX = Integer.signum(p.x() - totemPos.x()); // Direction in x
        int stepY = Integer.signum(p.y() - totemPos.y()); // Direction in y
        Position current = totemPos;
        while (!current.equals(p)) {
            if (isEmpty(current)) {
                return false;
            }

            current = new Position(current.x() + stepX, current.y() + stepY);
        }

        return true; // The path is full of obstacles
    }
    boolean checkLine(Position pos) {
        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return false; // Ensure position contains a Pawn

        Symbol symbol = t.getS();
        Color color = ((Pawn) t).getC();

        // Check left and right directions for symbol and color
        return countConsecutive(pos, symbol, true) >= 4 ||
                countConsecutive(pos, color, true) >= 4;
    }



    boolean checkColumn(Position pos) {
        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return false; // Ensure position contains a Pawn

        Symbol symbol = t.getS();
        Color color = ((Pawn) t).getC();

        // Check up and down directions for symbol and color
        return countConsecutive(pos, symbol, false) >= 4 ||
                countConsecutive(pos, color, false) >= 4;
    }


//todo: vérifier  que je ne sors pas du grid
    int countConsecutive(Position pos, Object attribute, boolean horizontal) {
        int count = 1; // Include the initial position
        int x = pos.x();
        int y = pos.y();

        // Check left/up
        for (int i = 1; i < 4; i++) {
            int newX = horizontal ? x : x - i;
            int newY = horizontal ? y - i : y;
            if (!isInside(new Position(newX, newY)) || !matchesAttribute(new Position(newX, newY), attribute)) {
                break;
            }
            count++;
        }

        // Check right/down
        for (int i = 1; i < 4; i++) {
            int newX = horizontal ? x : x + i;
            int newY = horizontal ? y + i : y;
            if (!isInside(new Position(newX, newY)) || !matchesAttribute(new Position(newX, newY), attribute)) {
                break;
            }
            count++;
        }

        return count;
    }

    public boolean matchesAttribute(Position pos, Object attribute) {
        if (!isInside(pos)) return false;

        Token t = grid[pos.x()][pos.y()];
        if (!(t instanceof Pawn)) return false;

        if (attribute instanceof Symbol) {
            return t.getS() == attribute;
        } else if (attribute instanceof Color) {
            return ((Pawn) t).getC() == attribute;
        }
        return false;
    }
    boolean checkWin(Position pos) {
        return checkLine(pos) || checkColumn(pos);
    }

}
