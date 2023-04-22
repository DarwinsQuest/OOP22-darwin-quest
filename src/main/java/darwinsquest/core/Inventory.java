package darwinsquest.core;

import java.util.Collection;

/**
 * Interface that represents an {@link Entity}'s
 * personal {@link Inventory}.
 */
public interface Inventory {

    /**
     * Retrieves the {@link Banion}(s) owned by the {@link Entity}.
     * @return A {@link Collection} of {@link Banion}(s).
     */
    Collection<Banion> getBanions();

    /**
     * Adds the given {@link Banion} into the {@link Entity}'s {@link Inventory}.
     * @param banion {@link Banion} to add.
     */
    void addBanion(Banion banion);

    /**
     * Removes the given {@link Banion} into the {@link Entity}'s {@link Inventory}.
     * @param banion {@link Banion} to remove.
     */
    void removeBanion(Banion banion);

}
