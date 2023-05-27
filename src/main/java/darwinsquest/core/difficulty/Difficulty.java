package darwinsquest.core.difficulty;

import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.world.BattleBoard;

/**
 * Interface of a game difficulty.
 */
public interface Difficulty {

    /**
     * The minimum amount of {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    int MIN_OPP_BANIONS = 1;
    /**
     * The maximum number of {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    int MAX_OPP_BANIONS = 4;

    /**
     * Provides the {@link AI} for this type of {@link Difficulty}.
     * @return the {@link AI}.
     */
    AI getAI();

    /**
     * Provides the {@link BattleBoard} for this type of {@link Difficulty}.
     * @return the {@link BattleBoard}.
     */
    BattleBoard getBoard();

    /**
     * Retrieves the {@link GameEntity}
     * that corresponds to a given {@code level}.
     * @param player the player that will fight the opponent.
     * @return the opponent.
     */
    GameEntity createOpponent(GameEntity player);
}
