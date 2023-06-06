package darwinsquest.core.battle;

import darwinsquest.core.battle.turn.Turn;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.util.Synchronizer;

import java.util.List;

/**
 * Interface that represents a tile that will prompt a battle.
*/
public interface BattleTile {

    /**
     * Starts the battle.
     * @return a list of turns that represent the actions made by the entities
     *         during the whole battle.
     */
    List<Turn> startBattle();

    /**
     * Retrieves a simple synchronizer.
     * @return a simple synchronizer.
     */
    Synchronizer getSynchronizer();

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
}
