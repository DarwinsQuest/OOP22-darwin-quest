package darwinsquest.core.decision;

import darwinsquest.core.battle.MoveTurnImpl;
import darwinsquest.core.battle.Turn;

/**
 * Class that represents the {@link darwinsquest.core.gameobject.entity.GameEntity}'s choice of
 * a {@link darwinsquest.core.gameobject.move.Move} during the battle.
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
