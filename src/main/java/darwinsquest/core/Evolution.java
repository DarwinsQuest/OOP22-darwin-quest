package darwinsquest.core;

/**
 * Interface that represents a {@link darwinsquest.core.gameobject.banion.Banion}'s
 * evolution path.
 */
public interface Evolution {

    /**
     * Prompts the {@link darwinsquest.core.gameobject.banion.Banion}'s evolution.
     */
    void evolve();

    /**
     * Determines whether the {@link darwinsquest.core.gameobject.banion.Banion} can evolve or not.
     * @return {@code true} if all evolving conditions are met,
     *         {@code false} otherwise.
     */
    boolean canEvolve();

}
