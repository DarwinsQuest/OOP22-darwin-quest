package darwinsquest.controller;

import darwinsquest.util.EObserver;

/**
 * Interface that denotes an observable of {@link BanionController} changes.
 */
public interface EObservableBanionControllerChanged {

    /**
     * Attaches a changed {@link BanionController} event observer.
     * @param observer the observer.
     * @return if operation done successfully.
     */
    boolean attachBanionChangedObserver(EObserver<? super BanionController> observer);

    /**
     * Detaches a changed {@link BanionController} event observer.
     * @param observer the observer.
     * @return if operation done successfully.
     */
    boolean detachBanionChangedObserver(EObserver<? super BanionController> observer);
}
