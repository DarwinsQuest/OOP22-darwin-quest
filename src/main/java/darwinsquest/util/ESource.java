package darwinsquest.util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * Class that represents an observable object.
 * @param <T> the observed type.
 */
public class ESource<T> implements EObservable<T> {

    private final Set<EObserver<? super T>> weakHashSet = Collections.newSetFromMap(new WeakHashMap<>());

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addEObserver(final EObserver<? super T> obs) {
        return weakHashSet.add(obs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean removeEObserver(final EObserver<? super T> obs) {
        return weakHashSet.remove(obs);
    }

    /**
     * Notifies listeners that this object state has changed.
     * @param arg the argument that was updated.
     */
    @Override
    public final void notifyEObservers(final T arg) {
        for (final var obs : weakHashSet) {
            obs.update(this, arg);
        }
    }
}
