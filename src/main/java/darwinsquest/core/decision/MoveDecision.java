package darwinsquest.core.decision;

import darwinsquest.core.MoveTurnImpl;
import darwinsquest.core.Turn;

/**
 * Class that represents the {@link darwinsquest.core.Entity}'s choice of
 * a {@link darwinsquest.core.Move} during the battle.
 */
public final class MoveDecision extends MiscDecision {

    /**
     * This constructor creates a new {@link MoveDecision}.
     */
    public MoveDecision() {
        super("Move");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Turn getAssociatedTurn(final Turn previousTurn) {
        return new MoveTurnImpl(previousTurn);
    }
}
