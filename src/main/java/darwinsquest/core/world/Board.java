package darwinsquest.core.world;

import java.util.OptionalInt;

/**
 * Interface that represents a sequence of tiles.
 */
public interface Board {

    /**
     * Retrieves the number of levels.
     * @return the number of levels, bound from 1 to number of levels + 1.
     */
    int getLevels();

    /**
     * Retrieves the position.
     * @return the position.
     */
    int getPos();

    /**
     * Tries to compute a movement of a certain step.
     * @return the step of movement, or {@link OptionalInt#empty()} if can't move.
     */
    OptionalInt move();
}
