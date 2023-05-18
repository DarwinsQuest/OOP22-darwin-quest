package darwinsquest.core;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Abstract class for interface Turn.
 */
public abstract class AbstractTurn implements Turn {

    private final UUID id;
    private final Pair<Entity, Optional<Banion>> entityOnTurn;
    private final Pair<Entity, Optional<Banion>> otherEntity;
    private boolean isStateLegal;

    /**
     * This constructor creates an {@link AbstractTurn} with the provided entities.
     * The currently deployed {@link Banion}s of the provided entities are automatically 
     * set to {@code null}.
     * @param entityOnTurn the entity will hold the current turn.
     * @param otherEntity the entity not on turn.
     */
    public AbstractTurn(final Entity entityOnTurn, final Entity otherEntity) {
        this.id = UUID.randomUUID();
        this.entityOnTurn = new MutablePair<>(Objects.requireNonNull(entityOnTurn), Optional.empty());
        this.otherEntity = new MutablePair<>(Objects.requireNonNull(otherEntity), Optional.empty());
    }

    /**
     * This constructor creates an {@link AbstractTurn} with the provided entities and
     * currently deployed {@link Banion}s.
     * @param entityOnTurn a pair composed by the {@link Entity} that will hold the turn 
     * and their currently deployed banion.
     * @param otherEntity a pair composed by the {@link Entity} that will not hold the turn
     * and their currently deployed banion.
     */
    public AbstractTurn(final Pair<Entity, Banion> entityOnTurn, final Pair<Entity, Banion> otherEntity) {
        if (Objects.nonNull(entityOnTurn) && Objects.nonNull(otherEntity)) {
            this.id = UUID.randomUUID();
            this.entityOnTurn = new MutablePair<>(Objects.requireNonNull(entityOnTurn.getLeft()),
                Optional.of(Objects.requireNonNull(entityOnTurn.getRight())));
            this.otherEntity = new MutablePair<>(Objects.requireNonNull(otherEntity.getLeft()),
                Optional.of(Objects.requireNonNull(otherEntity.getRight())));
        } else {
            throw new IllegalArgumentException("The tuples should not be null.");
        }
    }

    /**
     * Creates a new instance of {@link AbstractTurn} from the previous {@link AbstractTurn} of the battle.
     * @param turn the previous turn in the battle.
     */
    public AbstractTurn(final AbstractTurn turn) {
        if (Objects.nonNull(turn)) {
            this.id = UUID.randomUUID();
            this.entityOnTurn = turn.entityOnTurn;
            this.otherEntity = turn.otherEntity;
        } else {
            throw new IllegalArgumentException("turn should not be null.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Entity getEntityOnTurn() {
        // if (isStateLegal) {
            return this.entityOnTurn.getLeft();
        // } else {
            // throw new IllegalStateException("Turn must be done in order to call this method.");
        // }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Banion onTurnCurrentlyDeployedBanion() {
        if (isStateLegal) {
            return this.entityOnTurn.getRight().get(); // no control with isPresent() because if the state
                                                       // is legal then there is a banion
        } else {
            throw new IllegalStateException("Turn must be done in order to call this method.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Entity getOtherEntity() {
        // if (isStateLegal) {
            return this.otherEntity.getLeft();
        // } else {
            // throw new IllegalStateException("Turn must be done in order to call this method.");
        // }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Banion otherEntityCurrentlyDeployedBanion() {
        if (isStateLegal) {
            return this.otherEntity.getRight().get(); // no control with isPresent() because if the state is legal
                                                      // then there is a banion
        } else {
            throw new IllegalStateException("Turn must be done in order to call this method.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void performAction() {
        if (!isStateLegal) {
            isStateLegal = true; // isStateLegal is set to true here and not as last instruction of the if statement
                                 // because in this way the method doAction() can call the methods that require
                                 // that the state is legal.
            doAction();
        } else {
            throw new IllegalStateException("The action has already been done.");
        }
    }

    /**
     * Allows the {@link Entity} on turn to choose their action.
     * @see Turn#performAction()
     */
    protected abstract void doAction();

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(entityOnTurn, otherEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractTurn turn = (AbstractTurn) obj;
        return turn.id.equals(this.id);
    }

    /**
     * Retrieves whether the turn has been done.
     * @return {@code true} if the turn has already been done, {@code false} otherwise.
     */
    protected boolean isStateLegal() {
        return this.isStateLegal;
    }

    /**
     * Sets the currently deployed {@link Banion} of the {@link Entity} on turn.
     *
     * @param banion the new currently deployed banion of the entity on turn.
     */
    protected void setCurrentlyDeployedBanion(final Optional<Banion> banion) {
        entityOnTurn.setValue(banion);
    }

}
