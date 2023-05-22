package darwinsquest.core;

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
    public DeployTurnImpl(final Entity entityOnTurn, final Entity otherEntity) {
        super(entityOnTurn, otherEntity);
        this.deployedBanion = Optional.empty();
    }

    /**
     * Creates a new instance of {@link DeployTurnImpl} from the provided {@link Turn}.
     * The {@link Entity} on turn is the {@link Entity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link Entity} not on turn is the {@link Entity} that holds the turn in {@code previousTurn}.
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
            final var deployedBanion = this.deployedBanion.get(); // if the state is legal then the banion has already been
                                                                  // deployed, so the optional is not empty.
            return deployedBanion.copy();
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DeployTurnImpl[ " + internalState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        setCurrentlyDeployedBanion(Optional.of(getEntityOnTurn().deployBanion()));
        this.deployedBanion = onTurnCurrentlyDeployedBanion();
    }

}
