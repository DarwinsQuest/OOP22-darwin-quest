package darwinsquest.core.battle.turn;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.GameEntity;
import java.util.Optional;

/**
 * Interface that represents a battle turn.
 */
public interface Turn {

    /**
     * Retrieves the {@link GameEntity} that holds the turn.
     *
     * @return the entity that holds the turn.
     */
    GameEntity getEntityOnTurn();

    /**
     * Retrieves the currently deployed {@link Banion} of the {@link GameEntity}
     * on turn.
     *
     * @return {@link Optional#empty()} if the entity on turn has not yet deployed a banion or if the entity on turn
     *         is out of banions;
     *         an {@link Optional} containing the entity on turn's currently deployed banion otherwise.
     */
    Optional<Banion> onTurnCurrentlyDeployedBanion();

    /**
     * Retrieves the {@link GameEntity} that does not hold the turn.
     *
     * @return the entity that does not hold the turn.
     */
    GameEntity getOtherEntity();

    /**
     * Retrieves the currently deployed {@link Banion} of the {@link GameEntity}
     * not on turn.
     *
     * @return {@link Optional#empty()} if the entity not on turn has not yet deployed a banion, or if the entity not on turn
     *         is out of banions;
     *         an {@link Optional} containing the entity not on turn's currently deployed banion otherwise.
     */
    Optional<Banion> otherEntityCurrentlyDeployedBanion();

    /**
     * Allows the {@link GameEntity} on turn to effectuate their chosen action.
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
