package darwinsquest.controller;

import darwinsquest.core.gameobject.banion.Banion;

/**
 * A {@link Banion} wrapper class.
 */
interface BanionWrapper extends BanionController {

    /**
     * Retrieves the {@link Banion} that wraps.
     * @return the {@link Banion} that wraps.
     */
    Banion getBanion();
}
