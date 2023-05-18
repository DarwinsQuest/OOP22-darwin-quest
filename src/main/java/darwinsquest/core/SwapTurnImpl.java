package darwinsquest.core;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

/**
 * Implementation of {@link SwapTurn}.
 */
public class SwapTurnImpl extends AbstractTurn implements SwapTurn {

    private final Banion oldBanion;
    private Optional<Banion> newBanion;

    /**
     * This constructor creates an {@link SwapTurnImpl} with the provided entities and
     * currently deployed {@link Banion}s.
     *
     * @param entityOnTurn a pair composed by the {@link Entity} that will hold the turn
     *                     and their currently deployed banion.
     * @param otherEntity  a pair composed by the {@link Entity} that will not hold the turn
     *                     and their currently deployed banion.
     */
    public SwapTurnImpl(final Pair<Entity, Banion> entityOnTurn, final Pair<Entity, Banion> otherEntity) {
        super(entityOnTurn, otherEntity);
        oldBanion = onTurnCurrentlyDeployedBanion();
        newBanion = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        setCurrentlyDeployedBanion(getEntityOnTurn().swapBanion());
        this.newBanion = Optional.of(onTurnCurrentlyDeployedBanion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Banion, Optional<Banion>> getAction() {
        if (isStateLegal()) {
            return new ImmutablePair<>(oldBanion, newBanion);
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }

}
