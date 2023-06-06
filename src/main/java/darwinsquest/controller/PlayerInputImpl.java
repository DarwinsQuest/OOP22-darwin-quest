package darwinsquest.controller;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.battle.decision.MoveDecision;
import darwinsquest.core.battle.decision.SwapDecision;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.view.BattleInput;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;

/**
 * Class that manages player input.
 */
public class PlayerInputImpl implements PlayerInput {

    private final BattleInput input;
    private MoveController lastMove;
    private BanionController lastBanion;

    /**
     * Default constructor.
     * @param input the input class.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing a game entity is needed in the controller.")
    public PlayerInputImpl(final BattleInput input) {
        this.input = Objects.requireNonNull(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion deployBanion() {
        return ((BanionWrapper) input.deployBanion()).getBanion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move selectMove() {
        return ((MoveWrapper) lastMove).getMove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion swapBanion() {
        return ((BanionWrapper) lastBanion).getBanion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Decision getDecision() {
        final Decision decision;
        final var in = input.selectMoveOrBanion();
        if (in instanceof MoveController) {
            lastMove = (MoveController) in;
            decision = new MoveDecision();
        } else if (in instanceof BanionController) {
            lastBanion = (BanionController) in;
            decision = new SwapDecision();
        } else {
            throw new IllegalStateException("Decisions allowed are only Banion Move or Swap");
        }
        return decision;
    }
}
