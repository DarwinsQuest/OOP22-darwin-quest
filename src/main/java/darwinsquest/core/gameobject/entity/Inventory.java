package darwinsquest.core.gameobject.entity;

import darwinsquest.core.gameobject.banion.Banion;

import java.util.Collection;

/**
 * Interface that represents an {@link GameEntity}'s
 * personal {@link Inventory}.
 */
public interface Inventory {

    /**
     * Retrieves the {@link Banion}(s) owned by the {@link GameEntity}.
     * @return A {@link Collection} of {@link Banion}(s).
     */
    Collection<Banion> getBanions();

    /**
     * Adds the given {@link Banion} into the {@link GameEntity}'s {@link Inventory}.
     * @param banion {@link Banion} to add.
     */
    void addBanion(Banion banion);

    /**
     * Removes the given {@link Banion} into the {@link GameEntity}'s {@link Inventory}.
     * @param banion {@link Banion} to remove.
     */
    void removeBanion(Banion banion);

}
