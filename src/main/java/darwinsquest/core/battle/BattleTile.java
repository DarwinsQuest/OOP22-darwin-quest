package darwinsquest.core.battle;

import darwinsquest.core.battle.turn.Turn;
import darwinsquest.core.gameobject.entity.GameEntity;

import java.util.List;

/**
 * Interface that represents a tile that will prompt a battle.
*/
public interface BattleTile {

    /**
     * Retrieves the {@link GameEntity} that holds the first {@link Turn} of the battle.
     * @return the entity that holds the first turn.
     */
    GameEntity getPlayer();

    /**
     * Retrieves the {@link GameEntity} that doesn't hold the first {@link Turn} of the battle.
     * @return the entity that doesn't hold the first turn.
     */
    GameEntity getOpponent();

    /**
     * Starts a new battle by letting the two entities in the battle deploy their first banion.
     * Upon this method invocation, the player banions' hp will be restored to the full amount.
     * @return if the new battle has been created. A new battle cannot be created if the battle
     *         has already been done, and the previous winner of that battle is the player.
     */
    boolean newBattle();

    /**
     * Creates a new battle turn and lets the {@link darwinsquest.core.gameobject.entity.GameEntity}
     * on turn to perform their action.
     * @return if the new turn can be created. A new turn cannot be created if one of the two entities
     *         that are fighting in the battle is out of banions.
     * @see darwinsquest.core.gameobject.entity.GameEntity#isOutOfBanions()
     */
    boolean nextTurn();

    /**
     * Retrieves the list of {@link Turn} of the last fought battle.
     * If the battle has never started or the battle is being carried out, an empty
     * list will be returned.
     * @return the turns of the last fought battle.
     */
    List<Turn> getBattleTurns();

    /**
     * Retrieves if the provided {@link GameEntity} has won the battle.
     *
     * @param entity a {@link GameEntity} that is fighting in the battle.
     * @return {@code true} if {@code entity} has won the battle,
     *         <p>
     *         {@code false} if:
     *         <ul>
     *             <li> {@code entity} has lost the battle; or </li>
     *             <li> the battle has not been performed yet; or </li>
     *             <li> {@code entity} didn't fight in the battle. </li>
     *         </ul>
     */
    boolean isWinner(GameEntity entity);

    /**
     * Retrieves the minimum number of xp assigned to a player banion at the end of the battle.
     * @return the minimum xp assigned to a player banion.
     */
    int getMinXpBound();

}
