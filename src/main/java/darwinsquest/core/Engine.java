package darwinsquest.core;

import java.util.List;

/**
 * Interface for Model manager.
 */
public interface Engine {

    /**
     * Provides a sequence of possible difficulties for a new game to be created.
     * @return a sequence of possible difficulties.
     */
    List<String> getDifficulties();

    /**
     * Starts a new game.
     * @param difficulty a difficulty indicator selected between given {@link Engine#getDifficulties()} elements.
     * @return a {@link Board} object that represents this game sequence.
     * @see Board
     */
    Board startGame(String difficulty);

    /**
     * Tells if the game is over.
     * @return if the game is over.
     */
    boolean isGameOver();
}
