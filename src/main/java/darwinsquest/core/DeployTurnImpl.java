package darwinsquest.core;

import java.util.Optional;

/**
 * Implementation of {@link DeployTurn}.
 */
public class DeployTurnImpl extends AbstractTurn implements DeployTurn {

    private Optional<Banion> deployedBanion;

    /**
     * This constructor creates an {@link AbstractTurn} with the provided entities.
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
     * {@inheritDoc}
     */
    @Override
    public Banion getAction() {
        if (isStateLegal()) {
            final var deployedBanion = this.deployedBanion.get(); // if the state is legal then the banion has already been
                                                                  // deployed, so the optional is not empty.
            return new BanionImpl(deployedBanion.getElement(), deployedBanion.getName(), deployedBanion.getHp());
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        setCurrentlyDeployedBanion(Optional.of(getEntityOnTurn().deployBanion()));
        final var newBanion = onTurnCurrentlyDeployedBanion();
        this.deployedBanion = Optional.of(newBanion);
    }

}
