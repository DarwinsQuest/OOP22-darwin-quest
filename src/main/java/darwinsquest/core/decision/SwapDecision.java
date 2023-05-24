package darwinsquest.core.decision;

import darwinsquest.core.battle.SwapTurnImpl;
import darwinsquest.core.battle.Turn;

/**
 * Class that represents the {@link darwinsquest.core.gameobject.entity.GameEntity}'s choice of swapping the currently
 * deployed {@link darwinsquest.core.gameobject.banion.Banion} during a battle.
 */
public final class SwapDecision extends MiscDecision {

    /**
     * This constructor creates a new {@link SwapDecision}.
     */
    public SwapDecision() {
        super("Swap");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Turn getAssociatedTurn(final Turn previousTurn) {
        return new SwapTurnImpl(previousTurn);
    }

}
