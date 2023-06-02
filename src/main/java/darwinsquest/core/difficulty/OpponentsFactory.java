package darwinsquest.core.difficulty;

import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.world.Board;

/**
 * Interface that creates opponents.
 */
public interface OpponentsFactory {

    /**
     * Retrieves min opponent {@link darwinsquest.core.gameobject.banion.Banion}.
     * @return min opponent {@link darwinsquest.core.gameobject.banion.Banion}.
     */
    int getMinOpponentBanions();

    /**
     * Retrieves max opponent {@link darwinsquest.core.gameobject.banion.Banion}.
     * @return max opponent {@link darwinsquest.core.gameobject.banion.Banion}.
     */
    int getMaxOpponentBanions();

    /**
     * Retrieves the {@link darwinsquest.core.gameobject.entity.GameEntity} opponent.
     * @param board the board in which to fight the opponent.
     * @param player the player that will fight the opponent.
     * @return the opponent.
     */
    GameEntity createOpponent(Board board, GameEntity player);
}
