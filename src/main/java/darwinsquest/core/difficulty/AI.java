package darwinsquest.core.difficulty;

import java.util.Collection;
import java.util.Optional;

import darwinsquest.core.Banion;
import darwinsquest.core.Move;

/**
 * Interface that represents an {@link darwinsquest.core.Opponent}'s
 * {@link AI} difficulty.
 */
public interface AI {

    /**
     * Decides a {@link Banion} to deploy in battle.
     * @param banions the banions to choose from.
     * @return the {@link Banion} to deploy.
     * @see darwinsquest.core.Entity#deployBanion()
     */
    Banion decideBanionDeployment(Collection<Banion> banions);

    /**
     * Decides a {@link Move} to select.
     * @param moves the moves to choose from.
     * @return the selected {@link Move}.
     * @see darwinsquest.core.Entity#selectMove(Banion banion)
     */
    Move decideMoveSelection(Collection<Move> moves);

    /**
     * Decides a {@link Banion} to switch the currently active one with.
     * @param banions the banions owned by the {@link darwinsquest.core.Opponent}.
     * @return a {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     * @see darwinsquest.core.Entity#swapBanion()
     */
    Optional<Banion> decideBanionSwap(Collection<Banion> banions);

}
