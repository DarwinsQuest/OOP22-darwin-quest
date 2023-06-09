package darwinsquest.core.evolution;

import darwinsquest.core.gameobject.banion.Banion;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.function.Predicate;

/**
 * Interface that represents the ability to evolve.
 */
public interface Evolvable {

    /**
     * Prompts the {@link Banion}'s evolution.
     * @param requirement the condition to meet.
     * @return {@code true} if evolved.
     */
    boolean evolve(Predicate<Banion> requirement);

    /**
     * Prompts the {@link Banion}'s evolution to reach
     * an adequate phase based on the provided level.
     * <p>
     * The same requirement is applied to each evolution.
     * <p>
     * Upon the requirement failure, the evolution will be rollback
     * to the previous legal state.
     * <p>
     * No rollback will be prompted if the provided level is
     * less or equal to the current banion level.
     * @param level the level to reach.
     * @param requirement the condition to meet.
     * @return {@code true} if evolved,
     *         {@code false} in case of rollback, or if
     *         {@code level <= current level}.
     */
    boolean evolveToLevel(int level, Predicate<Banion> requirement);

    /**
     * Prompts the {@link Banion}'s evolution to reach an adequate
     * phase based on the provided level.
     * <p>
     * By utilising a multimap, this method enables the use of
     * different requirements across various level values while accommodating
     * the shared usage of a single requirement for multiple values.
     * <p>
     * Upon any requirement failure, the evolution will be rollback
     * to the previous legal state.
     * <p>
     * This multimap must contain all level values between the {@code current
     * level} (inclusive) and the given {@code level} (exclusive).
     * @param level the level to reach.
     * @param requirements a multimap that links a specific requirement
     *                     to a list of levels.
     * @return {@code true} if evolved,
     *         {@code false} in case of rollback.
     */
    boolean evolveToLevel(int level, MultiValuedMap<Predicate<Banion>, Integer> requirements);

}
