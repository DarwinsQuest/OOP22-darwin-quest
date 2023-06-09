package darwinsquest.core.evolution;

import darwinsquest.core.gameobject.banion.Banion;

import java.util.function.Predicate;

/**
 * Interface that represents a {@link Banion}'s
 * evolution path.
 */
@FunctionalInterface
public interface Evolution {

    /**
     * Prompts the {@link Banion}'s evolution.
     * @param banion the banion to evolve.
     * @param requirement the condition to meet.
     * @return {@code true} if evolved.
     */
    boolean evolve(Banion banion, Predicate<Banion> requirement);

}
