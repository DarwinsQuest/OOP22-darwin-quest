package darwinsquest.core.difficulty;

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
     * Provides the {@link BattleBoard} for this type of {@link Difficulty}.
     * @return the {@link BattleBoard}.
     */
    BattleBoard getBoard();
}
