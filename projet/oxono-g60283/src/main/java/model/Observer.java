package model;

/**
 * Observer is an interface that represents an object that observes changes in the state of a Game.
 * The observer will be notified whenever the game state changes, typically used for updating views.
 */
public interface Observer {
    /**
     * Called when the game state is updated.
     * The implementing class should define how to react to the game's state change.
     *
     * @param game The current game instance that is being observed.
     */
    void update(Game game);
}
