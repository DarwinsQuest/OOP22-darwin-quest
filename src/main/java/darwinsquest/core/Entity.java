package darwinsquest.core;

import java.util.Optional;

/**
 * Interface that represents in-game beings, both
 * playable and non-playable characters.
 */
public interface Entity {

    /**
     * Retrieves a string representing the {@link Entity}'s nickname.
     * @return the Entity's nickname.
     */
    String getNickname();

    /**
     * Retrieves the {@link Entity}'s personal {@link Inventory}.
     * @return the Entity's Inventory.
     */
    Inventory getInventory();

    /**
     * Retrieves the {@link Entity}'s chosen {@link Banion}
     * to be their active battle companion.
     * @return the chosen Banion.
     */
    Banion deployBanion();

    /**
     * Retrieves the {@link Entity}'s selected {@link Move}.
     * @return the selected Move.
     */
    Move selectMove();

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
