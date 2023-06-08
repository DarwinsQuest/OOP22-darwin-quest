package darwinsquest.core.difficulty;

import darwinsquest.core.world.BattleBoard;

/**
 * Interface of a game difficulty.
 */
public interface Difficulty {

    /**
     * Provides the {@link BattleBoard} for this type of {@link Difficulty}.
     * @return the {@link BattleBoard}.
     */
    BattleBoard getBoard();
}
