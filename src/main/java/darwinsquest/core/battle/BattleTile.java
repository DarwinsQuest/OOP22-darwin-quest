package darwinsquest.core.battle;

import darwinsquest.core.gameobject.entity.GameEntity;
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
     * @return {@code true} if {@code entity} has won the battle, {@code false} otherwise.
     */
    boolean isWinner(GameEntity entity);

}
