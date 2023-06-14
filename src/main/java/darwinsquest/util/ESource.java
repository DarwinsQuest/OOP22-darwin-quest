package darwinsquest.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Class that represents an observable object.
 * @param <T> the observed type.
 */
public class ESource<T> implements EObservable<T> {
    private final Set<EObserver<? super T>> hashSet = ConcurrentHashMap.newKeySet();

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addEObserver(final EObserver<? super T> obs) {
        return hashSet.add(obs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean removeEObserver(final EObserver<? super T> obs) {
        return hashSet.remove(obs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void notifyEObservers(final T arg) {
        for (final var obs : hashSet) {
            obs.update(this, arg);
        }
    }
}
