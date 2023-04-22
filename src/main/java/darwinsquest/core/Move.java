package darwinsquest.core;

/**
 * Interface that represents a move that can be performed in a battle.
 */
@FunctionalInterface
public interface Move {
    
    /**
     * Perform the {@link Move} on a banion.
     * @param banion The banion on which the {@link Move} is applied.
     */
    void perform(Banion banion);

}
