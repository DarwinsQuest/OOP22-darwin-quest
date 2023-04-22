package darwinsquest.core;

/**
 * Interface that represents the {@link Player}'s
 * battle opponents.
 */
public interface Opponent extends Entity {

    /**
     * Sets the {@link Opponent}'s {@link AI}
     * difficulty.
     * @param difficulty an {@link AI} object that
     *                   represents the opponent's
     *                   difficulty.
     */
    void setDifficulty(AI difficulty);

}
