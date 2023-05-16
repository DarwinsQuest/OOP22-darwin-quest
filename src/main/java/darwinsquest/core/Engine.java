package darwinsquest.core;

import java.util.List;
import java.util.Optional;

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
     * @return if the game is correctly started.
     */
    boolean startGame(String difficulty);

    /**
     * Retrieves a {@link Board} element.
     * @return the {@link Board}, or {@link Optional#empty()} if game isn't started yet.
     * @see Board
     */
    Optional<Board> getBoard();

    /**
     * Tells if the game is over.
     * @return if the game is over.
     */
    boolean isGameOver();
}
