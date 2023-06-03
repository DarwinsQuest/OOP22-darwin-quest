package darwinsquest;

import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.move.Move;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Optional;

/**
 * Class that represents a concrete Opponent controller.
 */
public class OpponentController extends AbstractEntityController {

    /**
     * Constructor for the opponent controller.
     * @param entity the game entity.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing a game entity is needed in the controller.")
    public OpponentController(final GameEntity entity) {
        super(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BanionController deployBanion() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move selectMove(final BanionController banion) {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BanionController> swapBanion() {
        throw new UnsupportedOperationException();
    }

    /**
     * {@inheritDoc}
     */
//    @Override
//    public DecisionController getDecision() {
//        throw new UnsupportedOperationException();
//    }

}
