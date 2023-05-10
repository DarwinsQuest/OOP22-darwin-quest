package darwinsquest.core;

/**
 * Interface of a game difficulty.
 */
public interface Difficulty {

    /**
     * Provides the {@link Board} for this kind of {@link Difficulty}.
     * @return the {@link Board}.
     */
    Board getBoard();
}
