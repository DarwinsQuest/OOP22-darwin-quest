package darwinsquest.core;

import java.util.Optional;

/**
 * Interface that represents a sequence of tiles.
 */
public interface Board {

    /**
     * Retrieves the number of levels.
     * @return The number of levels.
     */
    int getLevels();

    /**
     * Tries to compute a movement of a certain step.
     * @return The step of movement, or nothing if can't move.
     */
    Optional<Integer> move();
}
