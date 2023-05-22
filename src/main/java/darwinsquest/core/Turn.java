package darwinsquest.core;

import java.util.Optional;

/**
 * Interface that represents a battle turn.
 */
public interface Turn {

    /**
     * Retrieves the {@link Entity} that holds the turn.
     *
     * @return the entity that holds the turn.
     */
    Entity getEntityOnTurn();

    /**
     * Retrieves the currently deployed {@link Banion} of the entity
     * on turn.
     *
     * @return {@link Optional#empty()} if the entity on turn has not yet deployed a banion or if the entity on turn
     * is out of banions; an {@link Optional} containing the currently deployed banion otherwise.
     */
    Optional<Banion> onTurnCurrentlyDeployedBanion();

    /**
     * Retrieves the {@link Entity} that does not hold the turn.
     *
     * @return the entity that does not hold the turn.
     */
    Entity getOtherEntity();

    /**
     * Retrieves the currently deployed {@link Banion} of the {@link Entity}
     * not on turn.
     *
     * @return the currently deployed banion of the entity not on turn.
     */
    Optional<Banion> otherEntityCurrentlyDeployedBanion();

    /**
     * Allows the {@link Entity} on turn to effectuate their chosen action.
     *
     * @see AbstractTurn#doAction()
     */
    void performAction();

    /**
     * Retrieves whether the {@link Turn} has been done.
     *
     * @return {@code true} if the turn has already been performed, {@code false} otherwise.
     */
    boolean hasBeenDone();

}
