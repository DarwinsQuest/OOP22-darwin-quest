package darwinsquest.util;

/**
 * Interface that represents an observable object.
 * @param <T> the observed type.
 */
public interface EObservable<T> {

    /**
     * Adds a listener from the observers.
     * @param obs the listener.
     * @return if observer added correctly.
     */
    boolean addEObserver(AbstractEObserver<? super T> obs);

    /**
     * Removes a listener from the observers.
     * @param obs the listener.
     * @return if observer added correctly.
     */
    boolean removeEObserver(AbstractEObserver<? super T> obs);

    /**
     * Notifies all observers.
     * @param arg the argument that had been changed.
     */
    void notifyEObservers(T arg);
}
