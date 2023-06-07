package darwinsquest.util;

/**
 * Interface of a listener of changes.
 * @param <T> the type that has changed.
 */
@FunctionalInterface
public interface EObserver<T> {

    /**
     * Retrieves updated data to a listener.
     * @param s the source with changed state.
     * @param arg the argument changed.
     */
    void update(ESource<? extends T> s, T arg);
}
