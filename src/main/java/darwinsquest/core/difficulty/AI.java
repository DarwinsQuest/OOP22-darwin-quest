package darwinsquest.core.difficulty;

import darwinsquest.core.decision.Decision;

import java.util.Collection;
import java.util.Optional;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.Move;

/**
 * Interface that represents an {@link darwinsquest.core.gameobject.Opponent}'s
 * {@link AI} difficulty.
 */
public interface AI {

    /**
     * Decides a {@link Banion} to deploy in battle.
     * @param banions the banions to choose from.
     * @return the {@link Banion} to deploy.
     * @see darwinsquest.core.gameobject.Entity#deployBanion()
     */
    Banion decideBanionDeployment(Collection<Banion> banions);

    /**
     * Decides a {@link Move} to select.
     * @param moves the moves to choose from.
     * @return the selected {@link Move}.
     * @see darwinsquest.core.gameobject.Entity#selectMove(Banion banion)
     */
    Move decideMoveSelection(Collection<Move> moves);

    /**
     * Decides a {@link Banion} to switch the currently active one with.
     * @param banions the banions owned by the {@link darwinsquest.core.gameobject.Opponent}.
     * @return a {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     * @see darwinsquest.core.gameobject.Entity#swapBanion()
     */
    Optional<Banion> decideBanionSwap(Collection<Banion> banions);

    /**
     * Retrieves the decision made by the {@link AI} about what to do
     * in the following battle turn.
     * @return the decision made by the AI for the following battle turn.
     */
    Decision getDecision();

}
