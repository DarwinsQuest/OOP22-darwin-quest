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

    /**
     * Provides the attack stat amount.
     * @return The attack stat amount.
     */
    int getAtk();

    /**
     * Changes the attack stat amount.
     * @param amount The attack stat amount.
     */
    void setAtk(int amount);

    /**
     * Provides the defense stat amount.
     * @return The defense stat amount.
     */
    int getDef();

    /**
     * Changes the defense stat amount.
     * @param amount The defense stat amount.
     */
    void setDef(int amount);
}
