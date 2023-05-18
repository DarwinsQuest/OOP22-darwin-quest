package darwinsquest.core;

/**
 * Interface that represents a battle turn.
 */
public interface Turn {

    /**
     * Retrieves the {@link Entity} that holds the turn.
     * @return the entity that holds the turn.
     */
    Entity getEntityOnTurn();

    /**
     * Retrieves the currently deployed {@link Banion} of the entity
     * on turn.
     * @return the currently deployed banion of the entity on turn.
     */
    Banion onTurnCurrentlyDeployedBanion();

    /**
     * Retrieves the {@link Entity} that does not hold the turn.
     * @return the entity that does not hold the turn.
     */
    Entity getOtherEntity();

    /**
     * Retrieves the currently deployed {@link Banion} of the {@link Entity}
     * not on turn.
     * @return the currently deployed banion of the entity not on turn.
     */
    Banion otherEntityCurrentlyDeployedBanion();

    /**
     * Allows the {@link Entity} on turn to effectuate their chosen action.
     * @see AbstractTurn#doAction()
     */
    void performAction();

}
