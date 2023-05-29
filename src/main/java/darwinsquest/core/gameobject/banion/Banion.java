package darwinsquest.core.gameobject.banion;

import darwinsquest.core.gameobject.GameObject;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.util.Cloneable;

import java.util.Set;

/**
 * Interface that represents a game monster.
 */
public interface Banion extends Elemental, GameObject, Cloneable<Banion> {

    /**
     * Allowed number of moves.
     */
    int NUM_MOVES = 4;
    /**
     * Allowed min hit points amount.
     */
    int MIN_HP = 0;

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
}
