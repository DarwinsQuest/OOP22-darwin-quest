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
    boolean addEObserver(EObserver<? super T> obs);

    /**
     * Removes a listener from the observers.
     * @param obs the listener.
     * @return if observer added correctly.
     */
    boolean removeEObserver(EObserver<? super T> obs);
}
