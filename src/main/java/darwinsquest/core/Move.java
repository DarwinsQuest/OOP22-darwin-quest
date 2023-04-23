package darwinsquest.core;

/**
 * Interface that represents a move that can be performed in a battle.
 */
public interface Move {

    /**
     * Perform the {@link Move} on a banion.
     * @param banion The banion on which the {@link Move} is applied.
     */
    void perform(Banion banion);

    /**
     * Determines whether the {@link Move}'s effect takes into account the
     * number of the previous times said {@link Move} was performed.
     * @return {@code true} if the {@link Move} is stackable,
     *         {@code false} if not.
     */
    boolean isStackable();

    /**
     * Retrieves the amount of turns in which the {@link Move} 
     * cannot be used after its last use.
     * @return the cooldown turns of the {@link Move}.
    */
    int getCooldown();

    /**
     * Retrieves the amount of following turns in which the {@link Move}
     * has effect. 
     * @return the round duration of the {@link Move}.
    */
    int getDuration();

    /**
     * Retrieves a string representing the type of the {@link Move}.
     * @return the type of the {@link Move}.
     */
    String getType();

}
