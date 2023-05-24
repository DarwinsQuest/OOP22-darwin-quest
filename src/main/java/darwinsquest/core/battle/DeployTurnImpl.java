package darwinsquest.core.battle;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.GameEntity;
import java.util.Optional;

/**
 * Implementation of {@link DeployTurn}.
 */
public class DeployTurnImpl extends AbstractTurn implements DeployTurn {

    private Optional<Banion> deployedBanion;

    /**
     * This constructor creates an {@link DeployTurnImpl} with the provided entities.
     * The currently deployed {@link Banion}s of the provided entities are automatically
     * set to {@code null}.
     *
     * @param entityOnTurn the entity will hold the current turn.
     * @param otherEntity  the entity not on turn.
     */
    public DeployTurnImpl(final GameEntity entityOnTurn, final GameEntity otherEntity) {
        super(entityOnTurn, otherEntity);
        this.deployedBanion = Optional.empty();
    }

    /**
     * Creates a new instance of {@link DeployTurnImpl} from the provided {@link Turn}.
     * The {@link GameEntity} on turn is the {@link GameEntity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link GameEntity} not on turn is the {@link GameEntity} that holds the turn in {@code previousTurn}.
     * @param previousTurn the turn from which the new turn is created.
     */
    public DeployTurnImpl(final Turn previousTurn) {
        super(previousTurn);
        this.deployedBanion = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion getAction() {
        if (this.hasBeenDone()) {
            // final var deployedBanion = this.deployedBanion.get(); // if the state is legal then the banion has already been
                                                                  // deployed, so the optional is not empty.
            return this.deployedBanion.get();
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        if (this.hasBeenDone()) {
            return "DeployTurnImpl[ " + getEntityOnTurn().getName() + " deployed the banion " + deployedBanion.get();
        } else {
            return "The turn hasn't already been done.";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        final var currentBanion = Optional.of(getEntityOnTurn().deployBanion());
        setCurrentlyDeployedBanion(currentBanion);
        this.deployedBanion = Optional.of(currentBanion.get().copy());
    }

}
