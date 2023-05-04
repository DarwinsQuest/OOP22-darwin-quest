package darwinsquest.core;

import java.util.List;
import java.util.Optional;

/**
 * Interface that represents in-game beings, both
 * playable and non-playable characters.
 */
public interface Entity extends Nameable {

    /**
     * Retrieves the {@link Entity}'s personal inventory.
     * @return a list representing the inventory.
     */
    List<Banion> getInventory();

    /**
     * Updates the player's inventory.
     * If the provided index is grater than or equal the inventory size,
     * the banion is added at the given inventory index.
     * <p>
     * Example of adding a banion to an empty inventory:
     * <pre>{@code
     *     entity.getInventory().isEmpty();    // True.
     *     entity.updateInventory(0, banion);    // Adds the new banion at index 0.
     * }</pre>
     * <p>
     * Example of updating an existing banion:
     * <pre>{@code
     *     entity.getInventory().isEmpty();    // False.
     *     banion.setHp(50);    // Sets banion's hp at 50.
     *     entity.updateInventory(entity.getInventory().indexOf(banion), banion);    // Updates banion.
     * }</pre>
     * @param index of the banion to update.
     * @param banion the updated banion.
     */
    void updateInventory(int index, Banion banion);

    /**
     * Retrieves the {@link Entity}'s chosen {@link Banion}
     * to be their active battle companion.
     * @return the chosen Banion.
     */
    Banion deployBanion();

    /**
     * Retrieves the {@link Entity}'s selected {@link Move}
     * to be performed by a banion.
     * @param banion the banion to select the move from.
     * @return the selected move.
     */
    Move selectMove(Banion banion);

    /**
     * Swaps the active battle {@link Banion} with
     * another available one chosen by the {@link Entity}.
     * @return an {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     */
    Optional<Banion> swapBanion();

    /**
     * Determines whether the {@link Entity} has any {@link Banion}s left.
     * @return {@code true} if the {@link Entity} has no more available Banions.
     *         {@code false} if there are some left.
     */
    boolean isOutOfBanions();

}
