package darwinsquest.core.gameobject.banion;

import darwinsquest.util.AbstractEObserver;
import darwinsquest.util.EObserver;

/**
 * Interface that denotes an observable of {@link Banion} changes.
 */
public interface EObservableBanionChanged {

    /**
     * Attaches a changed {@link Banion} event observer.
     * @param observer the observer.
     * @return if operation done successfully.
     */
    boolean attachBanionChangedObserver(AbstractEObserver<? super Banion> observer);

    /**
     * Detaches a changed {@link Banion} event observer.
     * @param observer the observer.
     * @return if operation done successfully.
     */
    boolean detachBanionChangedObserver(AbstractEObserver<? super Banion> observer);
}
