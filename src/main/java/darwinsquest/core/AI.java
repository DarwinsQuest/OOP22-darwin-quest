package darwinsquest.core;

import java.util.Optional;

/**
 * Interface that represents an {@link Opponent}'s
 * {@link AI} difficulty.
 */
public interface AI {

    /**
     * Decides a {@link Banion} to deploy in battle.
     * @return the {@link Banion} to deploy.
     * @see Entity#deployBanion()
     */
    Banion decideBanionDeployment();

    /**
     * Decides a {@link Move} to select.
     * @return the selected {@link Move}.
     * @see Entity#selectMove()
     */
    Move decideMoveSelection();

    /**
     * Decides a {@link Banion} to switch the currently active one with.
     * @return a {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     * @see Entity#swapBanion()
     */
    Optional<Banion> decideBanionSwap();

}
