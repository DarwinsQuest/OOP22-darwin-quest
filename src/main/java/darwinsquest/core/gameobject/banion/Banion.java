package darwinsquest.core.gameobject.banion;

import darwinsquest.core.evolution.Evolvable;
import darwinsquest.core.gameobject.GameObject;
import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.util.Cloneable;
import darwinsquest.util.EObserver;

import java.util.Set;

/**
 * Interface that represents a game monster.
 */
public interface Banion extends Elemental, GameObject, Cloneable<Banion>, Evolvable {

    /**
     * Allowed number of moves.
     */
    int NUM_MOVES = 4;
    /**
     * Allowed min hit points amount.
     */
    int MIN_HP = 0;

    /**
     * Attaches an observer that is notified when this banion changes.
     * @param observer the observer.
     * @return if the operation was done successfully.
     */
    boolean attachBanionChangedObserver(EObserver<? super Banion> observer);

    /**
     * Detaches an observer that is notified when this banion changes.
     * @param observer the observer.
     * @return if the operation was done successfully.
     */
    boolean detachBanionChangedObserver(EObserver<? super Banion> observer);

    /**
     * Tells if this {@link Banion} is alive or not.
     * @return if is alive or not.
     */
    boolean isAlive();

    /**
     * Retrieves moves that can be performed.
     * @return moves that can be performed.
     * @see Move
     */
    Set<Move> getMoves();

    /**
     * Replaces the {@code old} move with the {@code new} one.
     * @param oldOne the {@link Move} to remove that has to be contained in {@link #getMoves()}.
     * @param newOne the {@link Move} to add that mustn't be contained in {@link #getMoves()}.
     * @return if {@code oldOne} replaced correctly with {@code newOne}.
     */
    boolean replaceMove(Move oldOne, Move newOne);

    /**
     * Provides the life stat amount.
     * @return the life stat amount.
     */
    int getHp();

    /**
     * Increases the life stat amount.
     * @param amount the life stat amount.
     */
    void increaseHp(int amount);

    /**
     * Decreases the life stat amount, if {@link #isAlive()}.
     * @param amount the life stat amount.
     */
    void decreaseHp(int amount);

    /**
     * Provides the full potential life amount.
     * @return the full potential life amount.
     */
    int getMaxHp();

    /**
     * Changes the max life stat amount.
     * @param amount the max life stat amount.
     */
    void setMaxHp(int amount);

    /**
     * Sets the life stat amount to {@link #getMaxHp()}.
     */
    void setHpToMax();

    /**
     * Increases the attack stat by the given amount.
     * @param amount the increase amount.
     */
    void increaseAttack(double amount);

    /**
     * Decreases the attack stat by the given amount.
     * @param amount the decrease amount.
     */
    void decreaseAttack(double amount);

    /**
     * Sets the attack stat value.
     * @param value the attack value.
     */
    void setAttack(double value);

    /**
     * Retrieves the attack stat current value.
     * @return the current attack value.
     */
    double getAttack();

    /**
     * Increases the defence stat by the given amount.
     * @param amount the increase amount.
     */
    void increaseDefence(double amount);

    /**
     * Decreases the defence stat by the given amount.
     * @param amount the decrease amount.
     */
    void decreaseDefence(double amount);

    /**
     * Sets the defence stat value.
     * @param value the defence value.
     */
    void setDefence(double value);

    /**
     * Retrieves the defence stat current value.
     * @return the current defence value.
     */
    double getDefence();

    /**
     * Retrieves the current banion level.
     * @return the current level.
     */
    int getLevel();

    /**
     * Increases the current level.
     */
    void increaseLevel();

    /**
     * Retrieves the current banion experience amount.
     * @return the current xp amount.
     */
    int getXp();

    /**
     * Retrieves the max xp amount.
     * @return the max xp.
     */
    int getMaxXp();

    /**
     * Increases the current experience amount.
     * <p>
     * This method will prompt an evolution and successively
     * put the remaining xp as the current xp value if
     * the amount surpasses the max xp allowed per level.
     * @param amount the increase.
     */
    void increaseXp(int amount);

}
