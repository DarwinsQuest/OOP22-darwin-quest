package darwinsquest.core.difficulty;

import darwinsquest.core.Board;

/**
 * Interface of a game difficulty.
 */
public interface Difficulty {

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
}
