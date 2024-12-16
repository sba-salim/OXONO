package model;

/**
 * Represents a Pawn in the game, a specific type of token with a color.
 * <p>
 * A Pawn is a game piece that inherits from the {@link Token} class and has both a symbol (X or O)
 * and a color (either PINK or BLACK). It represents a pawn on the game board that is placed by a player.
 *
 * @author Salim
 */
public class Pawn extends Token {
    private final Color c;

    /**
     * Returns the color of the Pawn.
     *
     * @return the color of the Pawn (PINK or BLACK).
     */
    public Color getC() {
        return c;
    }

    /**
     * Constructs a new Pawn with the given symbol and color.
     *
     * @param s the symbol of the Pawn (either X or O).
     * @param c the color of the Pawn (either PINK or BLACK).
     */
    public Pawn(Symbol s, Color c){
        super(s);
        this.c = c;
    }
}
