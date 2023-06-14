package darwinsquest.core.battle.turn;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import java.util.Optional;

/**
 * Implementation of {@link MoveTurn}.
 */
public class MoveTurnImpl extends AbstractTurn implements MoveTurn {

    private Move actionDone;
    private final Banion activeBanion; // the banion which performs the move
    private final Banion passiveBanion; // the banion on which the move is performed
    private final Banion activeBanionCopy;
    private Optional<Banion> passiveBanionCopy;

    /**
     * Creates a new instance of {@link MoveTurnImpl} from the provided {@link Turn}.
     * The {@link darwinsquest.core.gameobject.entity.GameEntity} on turn
     * is the {@link darwinsquest.core.gameobject.entity.GameEntity} that does not hold the turn in {@code previousTurn}.
     * As a consequence the {@link darwinsquest.core.gameobject.entity.GameEntity} not on turn is
     * the {@link darwinsquest.core.gameobject.entity.GameEntity} that holds the turn in {@code previousTurn}.
     * @param previousTurn the previous turn in the battle.
     */
    public MoveTurnImpl(final Turn previousTurn) {
        super(previousTurn);
        if (previousTurn.onTurnCurrentlyDeployedBanion().isPresent()
                && previousTurn.otherEntityCurrentlyDeployedBanion().isPresent()) {
            activeBanion = previousTurn.otherEntityCurrentlyDeployedBanion().get();
            passiveBanion = previousTurn.onTurnCurrentlyDeployedBanion().get();
            activeBanionCopy = activeBanion.copy();
            passiveBanionCopy = Optional.empty();
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
            return new ImmutableTriple<>(actionDone, activeBanionCopy, passiveBanionCopy.get());
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
            return "MoveTurnImpl[ " + getEntityOnTurn().getName() + " performed the move " + getAction().getLeft()
                    + " with the banion " + getAction().getMiddle() + " against the banion " + getAction().getRight();
        } else {
            return "The turn hasn't already been done.";
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doAction() {
        this.actionDone = getEntityOnTurn().selectMove(activeBanion);
        // the chosen move is always done against otherEntityCurrentlyDeployedBanion().get().
        actionDone.perform(activeBanion, passiveBanion);
        /*
         * I decided to store a copy of the real banions because, in this way, if is wanted to show
         * a sort of report of the battle at its end, all the banions have the same hp that they had at
         * the exact moment the turn was carried out.
         */
        passiveBanionCopy = Optional.of(passiveBanion.copy());
    }

}
