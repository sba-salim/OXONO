package model;

/**
 * Abstract class representing a generic game token.
 * <p>
 * A token has a symbol associated with it. This class serves as a base class for different types of tokens
 * that can exist in the game (e.g., Totem, Pawn). The symbol is used to distinguish between different players
 * or types of game pieces.
 *
 * @author Salim
 */
public abstract class Token {
    private final Symbol s;

    /**
     * Constructs a new Token with the given symbol.
     *
     * @param s the symbol representing the token (either X or O).
     */
    public Token(Symbol s) {
        this.s = s;
    }

    /**
     * Returns the symbol of the token.
     *
     * @return the symbol of this token (either X or O).
     */
    public Symbol getS() {
        return s;
    }
}
