package darwinsquest.controller;

import darwinsquest.util.AbstractEObserver;
import darwinsquest.util.EObserver;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Interface that represents the {@link darwinsquest.core.gameobject.entity.GameEntity}'s controller.
 */
public interface EntityController {

    /**
     * Retrieves the entity's name.
     * @return the entity's name.
     */
    String getName();

    /**
     * Retrieves the game entity's personal inventory.
     * @return a list representing the inventory.
     */
    List<BanionController> getInventory();

    /**
     * Appends the banion to the end of the inventory.
     * @param banion the new banion.
     * @return {@code true} on successful operation,
     *         {@code false} if not or if the provided banion is
     *         already present.
     */
    boolean addToInventory(BanionController banion);

    /**
     * Appends all the banions in the given collection to the end of the inventory,
     * in the order that they are returned by the specified collection's iterator.
     * @param banions collection of banions to append in the inventory.
     * @return {@code true} on successful operation,
     *         {@code false} if not or if the provided banions are already present.
     */
    boolean addToInventory(Collection<BanionController> banions);

    /**
     * Updates the player's inventory by switching {@code oldBanion} with {@code newBanion}.
     * @param oldBanion the banion to update.
     * @param newBanion the updated banion.
     * @return an optional of {@code oldBanion} on successful operation,
     *         empty if neither {@code oldBanion} nor {@code newBanion} are contained in the inventory.
     */
    Optional<BanionController> updateInventory(BanionController oldBanion, BanionController newBanion);

    /**
     * Determines whether the game entity has any banions left.
     * @return {@code true} if the game entity has no more available banions.
     *         {@code false} if there are some left.
     */
    boolean isOutOfBanions();

    /**
     * Attaches a swap banion observer.
     * @param observer observer
     * @return {@code true} on successful operation
     */
    boolean attachSwapBanionObserver(AbstractEObserver<? super BanionController> observer);

    /**
     * Detaches a swap banion observer.
     * @param observer observer
     * @return {@code true} on successful operation
     */
    boolean detachSwapBanionObserver(AbstractEObserver<? super BanionController> observer);

}
