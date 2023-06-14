package darwinsquest.core.difficulty;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.banion.Banion;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface that represents an {@link darwinsquest.core.gameobject.entity.Opponent}'s
 * {@link AI} difficulty.
 */
public interface AI {

    /**
     * Decides a {@link Banion} to deploy in battle.
     * @param banions the banions to choose from.
     * @return the {@link Banion} to deploy.
     * @see darwinsquest.core.gameobject.entity.GameEntity#deployBanion()
     */
    Banion decideBanionDeployment(Collection<Banion> banions);

    /**
     * Decides a {@link Move} to select.
     * @param moves the moves to choose from.
     * @return the selected {@link Move}.
     * @see darwinsquest.core.gameobject.entity.GameEntity#selectMove(Banion banion)
     */
    Move decideMoveSelection(Collection<Move> moves);

    /**
     * Decides a {@link Banion} to switch the currently active one with.
     * @param banions the banions owned by the {@link darwinsquest.core.gameobject.entity.Opponent}.
     * @return a {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     * @see darwinsquest.core.gameobject.entity.GameEntity#swapBanion()
     */
    Optional<Banion> decideBanionSwap(Collection<Banion> banions);

    /**
     * Retrieves the decision made by the {@link AI} about what to do
     * in the following battle turn.
     * @return the decision made by the AI for the following battle turn.
     */
    Decision getDecision();

}
