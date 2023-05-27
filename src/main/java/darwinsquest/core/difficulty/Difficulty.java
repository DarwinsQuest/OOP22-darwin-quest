package darwinsquest.core.difficulty;

import darwinsquest.core.gameobject.entity.Opponent;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.core.world.Board;

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
     * Provides the {@link Board} for this type of {@link Difficulty}.
     * @return the {@link Board}.
     */
    Board getBoard();

    /**
     * Retrieves the {@link Opponent}
     * that corresponds to a given {@code level}.
     * @param player the player that will have to face with the {@link Opponent}.
     * @return the opponent.
     */
    Opponent getOpponent(Player player);
}
