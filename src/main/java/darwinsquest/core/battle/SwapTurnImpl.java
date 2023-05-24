package darwinsquest.core.battle;

import darwinsquest.core.gameobject.banion.Banion;
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
     * Creates a new instance of {@link SwapTurnImpl} from the provided {@link Turn}.
     * The {@link darwinsquest.core.gameobject.entity.GameEntity} on turn is
     * the {@link darwinsquest.core.gameobject.entity.GameEntity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link darwinsquest.core.gameobject.entity.GameEntity} not on turn is
     * the {@link darwinsquest.core.gameobject.entity.GameEntity} that holds the turn in {@code previousTurn}.
     * @param previousTurn the previous turn in the battle.
     */
    public SwapTurnImpl(final Turn previousTurn) {
        super(previousTurn);
        if (previousTurn.otherEntityCurrentlyDeployedBanion().isPresent()) {
            oldBanion = previousTurn.otherEntityCurrentlyDeployedBanion().get().copy(); // the other entity of the previous turn
                                                                                        // is the entity on turn of this turn.
            newBanion = Optional.empty();
        } else {
            throw new IllegalArgumentException("The entity not on turn in previousTurn must have a currently deployed banion.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Banion, Optional<Banion>> getAction() {
        if (hasBeenDone()) {
            return new ImmutablePair<>(oldBanion, Optional.of(newBanion.get()));
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
            return "SwapTurnImpl[ " + getEntityOnTurn().getName() + " swapped the banion " + getAction().getLeft()
                    + " with the banion " + getAction().getRight();
        } else {
            return "The turn hasn't already been done.";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        final var chosenBanion = getEntityOnTurn().swapBanion();
        setCurrentlyDeployedBanion(chosenBanion);
        this.newBanion = Optional.of(chosenBanion.get().copy());
    }

}
