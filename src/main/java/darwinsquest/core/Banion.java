package darwinsquest.core;

import java.util.Collection;

/**
 * Interface that represents a game monster.
 */
public interface Banion extends Elemental, Nameable {

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
     * Adds a move to the possible moves to perform.
     * @param move the move to add.
     * @return {@code true} if move added correctly.
     * @see Move
     */
    boolean learnMove(Move move);

    /**
     * Removes a specific move.
     * @param move the move that will be removed.
     * @return if move removed correctly.
     * @see Move
     */
    boolean forgetMove(Move move);

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
