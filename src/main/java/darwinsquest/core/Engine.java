package darwinsquest.core;

import java.util.List;

/**
 * Interface for Model manager.
 */
public interface Engine {

    /**
     * Provides a sequence of possible difficulties for a new game to be created.
     * @return A sequence of possible difficulties.
     */
    List<String> getDifficulties();

    /**
     * Starts a new game.
     * @param difficulty A difficulty indicator selected between given {@link Engine#getDifficulties()} elements.
     * @return A {@link Board} object that represents this game sequence.
     */
    Board startGame(String difficulty);

    /**
     * Tells if the game is over.
     * @return If the game is over.
     */
    boolean isGameOver();
}
