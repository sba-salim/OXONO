package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Player in the game, who has a hand of pawns and a color.
 * <p>
 * A Player is characterized by a color (either PINK or BLACK) and a hand of pawns. The player can have
 * pawns with two symbols (X and O), and can place or remove them from their hand during the game.
 * <p>
 * The player also has a set of actions they can perform, such as adding or removing pawns from their hand,
 * checking if they have a specific pawn, or determining if their hand is empty.
 *
 * @author Salim
 */
public class Player {
    private final List<Pawn> hand;
    private final Color c;

    /**
     * Constructs a new Player with the given color and hand size.
     * The hand is initialized with a certain number of pawns (half X and half O).
     *
     * @param c the color of the Player (either PINK or BLACK).
     * @param handSize the number of pawns the Player starts with.
     */
    public Player(Color c, int handSize) {
        this.c = c;
        this.hand = new ArrayList<>();
        for (int i = 0; i < handSize/2; i++) {
            Pawn p = new Pawn(Symbol.X, c);
            hand.add(p);
            Pawn p2 = new Pawn(Symbol.O, c);
            hand.add(p2);
        }
    }

    /**
     * Returns the number of X pawns the Player has in their hand.
     *
     * @return the number of X pawns in the Player's hand.
     */
    public int XPawns() {
        int n = 0;
        for (Pawn pawn : hand) {
            if (pawn.getS() == Symbol.X)
                n++;
        }
        return n;
    }

    /**
     * Returns the number of O pawns the Player has in their hand.
     *
     * @return the number of O pawns in the Player's hand.
     */
    public int OPawns() {
        int n = 0;
        for (Pawn pawn : hand) {
            if (pawn.getS() == Symbol.O)
                n++;
        }
        return n;
    }

    /**
     * Checks if the Player has a pawn of the given symbol in their hand.
     *
     * @param s the symbol of the pawn to search for (either X or O).
     * @return true if the Player has the pawn, false otherwise.
     */
    public boolean hasPawn(Symbol s) {
        for (Pawn pawn : hand) {
            if (pawn.getS() == s) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the Player's hand is empty.
     *
     * @return true if the Player has no pawns in their hand, false otherwise.
     */
    public boolean isHandEmpty() {
        return this.hand.isEmpty();
    }

    /**
     * Adds a pawn to the Player's hand.
     *
     * @param pawn the Pawn to be added.
     */
    void addPawn(Pawn pawn) {
        hand.add(pawn);
    }

    /**
     * Removes a pawn with the specified symbol from the Player's hand.
     *
     * @param s the symbol of the pawn to be removed (either X or O).
     * @return the removed Pawn, or null if no pawn with the given symbol was found.
     */
    Pawn removePawn(Symbol s) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getS() == s) {
                return hand.remove(i);
            }
        }
        return null;
    }

    /**
     * Returns the color of the Player.
     *
     * @return the color of the Player (either PINK or BLACK).
     */
    public Color getC() {
        return c;
    }
}
