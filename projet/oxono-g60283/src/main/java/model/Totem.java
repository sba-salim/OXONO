package model;

/**
 * Represents a Totem in the game, a specific type of token.
 * <p>
 * A Totem is a game piece that inherits from the {@link Token} class and has a symbol associated with it.
 * It is used to represent a player's totem on the board. The symbol of the Totem will be either X or O,
 * depending on the player it belongs to.
 *
 * @author Salim
 */
public class Totem extends Token {

    /**
     * Constructs a new Totem with the given symbol.
     *
     * @param s the symbol of the Totem (either X or O).
     */
    public Totem(Symbol s) {
        super(s);
    }
}
