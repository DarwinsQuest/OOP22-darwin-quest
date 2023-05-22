package darwinsquest.core;

import java.util.Collection;

/**
 * Interface that represents a game monster.
 */
public interface Banion extends Elemental, Nameable, Cloneable<Banion> {

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
    Collection<Move> getMoves();

    /**
     * Replaces the {@code old} move with the {@code new} one.
     * @param oldOne the {@link Move} to remove.
     * @param newOne the {@link Move} to add.
     * @return if {@code oldMove} replaced correctly.
     */
    boolean replaceMove(Move oldOne, Move newOne);

    /**
     * Provides the life stat amount.
     * @return the life stat amount.
     */
    int getHp();

    /**
     * Changes the life stat amount.
     * @param amount the life stat amount.
     */
    void setHp(int amount);
}
