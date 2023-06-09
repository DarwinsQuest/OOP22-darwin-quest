package darwinsquest.util;

/**
 * Class that represents an observable object.
 * @param <T> the observed type.
 */
public class ESource<T> implements EObservable<T> {

//    private final Set<EObserver<? super T>> hashSet = ConcurrentHashMap.newKeySet();
    private final SetWithRemove<AbstractEObserver<? super T>> hashSet = new SetWithRemove<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean addEObserver(final AbstractEObserver<? super T> obs) {
        return hashSet.add(obs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean removeEObserver(final AbstractEObserver<? super T> obs) {
        return hashSet.remove(obs);
    }

    /**
     * Notifies listeners that this object state has changed.
     * @param arg the argument that was updated.
     */
    @Override
    public final void notifyEObservers(final T arg) {
        for (final var obs : hashSet) {
            obs.update(this, arg);
        }
    }
}
