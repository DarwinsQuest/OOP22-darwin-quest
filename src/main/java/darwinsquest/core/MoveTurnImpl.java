package darwinsquest.core;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Implementation of {@link MoveTurn}.
 */
public class MoveTurnImpl extends AbstractTurn implements MoveTurn {

    private Move actionDone;
    private final Banion activeBanion;
    private final Banion passiveBanion;

    /**
     * Creates a new instance of {@link MoveTurnImpl} from the provided {@link Turn}.
     * The {@link Entity} on turn is the {@link Entity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link Entity} not on turn is the {@link Entity} that holds the turn in {@code previousTurn}.
     * @param previousTurn the previous turn in the battle.
     */
    public MoveTurnImpl(final Turn previousTurn) {
        super(previousTurn);
        if (previousTurn.onTurnCurrentlyDeployedBanion().isPresent()
                && previousTurn.otherEntityCurrentlyDeployedBanion().isPresent()) {
            activeBanion = previousTurn.otherEntityCurrentlyDeployedBanion().get();
            passiveBanion = previousTurn.onTurnCurrentlyDeployedBanion().get();
        } else {
            throw new IllegalArgumentException("All the entities must have a currently deployed banion.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Triple<Move, Banion, Banion> getAction() {
        if (hasBeenDone()) {
            return new ImmutableTriple<>(actionDone, activeBanion, passiveBanion);
        } else {
            throw new IllegalStateException("The action hasn't already been done.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "MoveTurnImpl[ " + internalState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        this.actionDone = getEntityOnTurn().selectMove(activeBanion);
        actionDone.perform(passiveBanion); // the chosen move is always done against otherEntityCurrentlyDeployedBanion().get().
    }

}
