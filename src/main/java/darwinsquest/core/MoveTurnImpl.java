package darwinsquest.core;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Implementation of {@link MoveTurn}.
 */
public class MoveTurnImpl extends AbstractTurn implements MoveTurn {

    private Move actionDone;

    /**
     * This constructor creates an {@link AbstractTurn} with the provided entities and
     * currently deployed {@link Banion}s.
     * @param entityOnTurn a pair composed by the {@link Entity} that will hold the turn
     *                     and their currently deployed banion.
     * @param otherEntity  a pair composed by the {@link Entity} that will not hold the turn
     *                     and their currently deployed banion.
     */
    public MoveTurnImpl(final Pair<Entity, Banion> entityOnTurn, final Pair<Entity, Banion> otherEntity) {
        super(entityOnTurn, otherEntity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Triple<Move, Banion, Banion> getAction() {
        if (isStateLegal()) {
            return new ImmutableTriple<>(actionDone, onTurnCurrentlyDeployedBanion(), otherEntityCurrentlyDeployedBanion());
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        this.actionDone = getEntityOnTurn().selectMove(onTurnCurrentlyDeployedBanion());
        actionDone.perform(otherEntityCurrentlyDeployedBanion());
    }

}
