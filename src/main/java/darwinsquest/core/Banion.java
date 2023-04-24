package darwinsquest.core;

/**
 * Interface that represents a game monster.
 */
public interface Banion {

    /**
     * Tells if this {@link Banion} is alive or not.
     * @return if is alive or not.
     */
    boolean isAlive();

    /**
     * Provides the life stat amount.
     * @return The life stat amount.
     */
    int getHp();

    /**
     * Changes the life stat amount.
     * @param amount The life stat amount.
     */
    void setHp(int amount);
}
