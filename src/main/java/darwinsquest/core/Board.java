package darwinsquest.core;

import java.util.Collection;
import java.util.Optional;

/**
 * Interface that represents a sequence of tiles.
 */
public interface Board {

    /**
     * Provides shop tiles positions.
     * @return Shop tiles positions.
     */
    Collection<Integer> getShopsPos();

    /**
     * Provides special tiles positions.
     * @return Special tiles positions.
     */
    Collection<Integer> getSpecialsPos();

    /**
     * Provides move tiles positions.
     * @return Move tiles positions.
     */
    Collection<Integer> getMovementsPos();

    /**
     * Provides level tiles positions.
     * @return Level tiles positions.
     */
    Collection<Integer> getLevelsPos();

    /**
     * Tries to compute a movement of a certain step.
     * @return The step of movement, or nothing if can't move.
     */
    Optional<Integer> move();
}
