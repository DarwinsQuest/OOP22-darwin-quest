package darwinsquest.core;

import darwinsquest.core.world.BattleBoard;

import java.util.Optional;
import java.util.Set;

/**
 * Interface for Model manager.
 */
public interface Engine {

    /**
     * Provides an ordered sequence of possible difficulties for a new game to be created.
     * @return an ordered sequence of possible difficulties.
     */
    Set<String> getDifficulties();

    /**
     * Starts a new game.
     * @param difficulty a difficulty indicator selected between given {@link Engine#getDifficulties()} elements.
     * @return if the game is correctly started.
     */
    boolean startGame(String difficulty);

    /**
     * Retrieves a {@link darwinsquest.core.world.Board} element.
     * @return the {@link darwinsquest.core.world.Board}, or {@link Optional#empty()} if game isn't started yet.
     * @see darwinsquest.core.world.Board
     */
    Optional<BattleBoard> getBoard();

    /**
     * Tells if the game is over.
     * @return if the game is over.
     */
    boolean isGameOver();
}
