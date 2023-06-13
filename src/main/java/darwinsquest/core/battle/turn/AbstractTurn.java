package darwinsquest.core.battle.turn;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.GameEntity;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Abstract class for interface Turn.
 */
public abstract class AbstractTurn implements Turn {

    private final UUID id;
    private final Pair<GameEntity, Optional<Banion>> entityOnTurn;
    private final Pair<GameEntity, Optional<Banion>> otherEntity;
    private boolean isStateLegal;

    /**
     * This constructor creates an {@link AbstractTurn} with the provided entities.
     * The currently deployed {@link Banion}s of the provided entities are automatically 
     * set to {@link Optional#empty()}.
     * @param entityOnTurn the entity will hold the current turn.
     * @param otherEntity the entity not on turn.
     */
    public AbstractTurn(final GameEntity entityOnTurn, final GameEntity otherEntity) {
        this.id = UUID.randomUUID();
        if (Objects.nonNull(entityOnTurn) && Objects.nonNull(otherEntity)) {
            this.entityOnTurn = new MutablePair<>(entityOnTurn, Optional.empty());
            this.otherEntity = new MutablePair<>(otherEntity, Optional.empty());
        } else {
            throw new IllegalArgumentException("The entities cannot be null.");
        }
    }

    /**
     * Creates a new instance of {@link AbstractTurn} from the provided {@link Turn}.
     * The {@link GameEntity} on turn is the {@link GameEntity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link GameEntity} not on turn is the {@link GameEntity} that holds the turn
     * in {@code previousTurn}.
     * @param previousTurn the previous turn in the battle.
     */
    public AbstractTurn(final Turn previousTurn) {
        if (Objects.nonNull(previousTurn) && previousTurn.hasBeenDone()) {
            this.id = UUID.randomUUID();
            this.entityOnTurn = new MutablePair<>(previousTurn.getOtherEntity(),
                    previousTurn.otherEntityCurrentlyDeployedBanion());
            this.otherEntity = new MutablePair<>(previousTurn.getEntityOnTurn(),
                    previousTurn.onTurnCurrentlyDeployedBanion());
        } else {
            throw new IllegalArgumentException("turn cannot be null or cannot not be unperformed.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameEntity getEntityOnTurn() {
        return this.entityOnTurn.getLeft();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<Banion> onTurnCurrentlyDeployedBanion() {
        if (isStateLegal) {
            return this.entityOnTurn.getRight();
        } else {
            throw new IllegalStateException("Turn must be done in order to call this method.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final GameEntity getOtherEntity() {
        return this.otherEntity.getLeft();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<Banion> otherEntityCurrentlyDeployedBanion() {
        if (isStateLegal) {
            return this.otherEntity.getRight();
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
            isStateLegal = true;
            doAction();
        } else {
            throw new IllegalStateException("The action has already been done.");
        }
    }

    /**
     * Allows the {@link GameEntity} on turn to choose their action.
     * @see Turn#performAction()
     */
    protected abstract void doAction();

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, entityOnTurn, otherEntity);
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
     * {@inheritDoc}
     */
    @Override
    public boolean hasBeenDone() {
        return this.isStateLegal;
    }

    /**
     * Sets the currently deployed {@link Banion} of the {@link GameEntity} on turn.
     *
     * @param banion the new currently deployed banion of the entity on turn.
     */
    protected void setCurrentlyDeployedBanion(final Optional<Banion> banion) {
        if (hasBeenDone()) {
            entityOnTurn.setValue(banion);
        }
    }

}
