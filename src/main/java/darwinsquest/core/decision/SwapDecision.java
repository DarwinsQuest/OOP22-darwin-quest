package darwinsquest.core.decision;

import darwinsquest.core.SwapTurnImpl;
import darwinsquest.core.Turn;

/**
 * Class that represents the {@link darwinsquest.core.Entity}'s choice of swapping the currently
 * deployed {@link darwinsquest.core.Banion} during a battle.
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
