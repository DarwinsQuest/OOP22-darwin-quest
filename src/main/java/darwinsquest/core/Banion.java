package darwinsquest.core;

import java.util.Collection;

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
     * Retrieves moves that can be performed.
     * @return Moves that can be performed.
     */
    Collection<Move> getMoves();

    /**
     * Adds a {@link Move} to the possible moves to perform.
     * @param move A move.
     * @return {@code true} if move added correctly.
     */
    boolean learnMove(Move move);

    /**
     * Removes a specific move.
     * @param move The move that will be removed.
     * @return If move removed correctly.
     */
    boolean forgetMove(Move move);

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
