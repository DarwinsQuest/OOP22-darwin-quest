package darwinsquest.core.difficulty;

import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.entity.Opponent;
import darwinsquest.core.world.Board;

/**
 * Interface that creates opponents.
 */
public interface OpponentsFactory {

    /**
     * Retrieves the {@link darwinsquest.core.gameobject.entity.GameEntity} opponent.
     * @param board the board in which to fight the opponent.
     * @param player the player that will fight the opponent.
     * @return the opponent.
     */
    Opponent createOpponent(Board board, GameEntity player);
}
