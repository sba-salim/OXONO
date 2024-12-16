package model;

/**
 * Observable is an interface that represents an object whose state can be observed by other objects (observers).
 * It provides methods for registering, removing, and notifying observers when the state changes.
 */
public interface Observable {

    /**
     * Registers an observer to receive notifications about changes in the state of the object.
     * The observer will be notified whenever the state of the object changes.
     *
     * @param observer The observer to be registered.
     */
    void registerObserver(Observer observer);

    /**
     * Removes an observer from the list of registered observers.
     * The observer will no longer receive notifications when the state changes.
     *
     * @param observer The observer to be removed.
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all registered observers that the state of the object has changed.
     * This method should be called when the state changes to inform all observers.
     */
    void notifyObservers();
}
