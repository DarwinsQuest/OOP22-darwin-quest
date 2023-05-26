package darwinsquest.core;

import org.apache.commons.collections4.MultiValuedMap;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Interface that represents a game monster.
 */
public interface Banion extends Elemental, Nameable, Cloneable<Banion> {

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
     * Retrieves the current banion level.
     * @return the current level.
     */
    int getLevel();

    /**
     * Increases the current level.
     */
    void increaseLevel();

    /**
     * Prompts the {@link Banion}'s evolution.
     * @param requirement the condition to meet.
     * @return {@code true} if evolved.
     */
    boolean evolve(Predicate<Banion> requirement);

    /**
     * Prompts the {@link Banion}'s evolution to reach
     * an adequate phase based on the provided level.
     * <p>
     * The same requirement is applied to each evolution.
     * <p>
     * Upon the requirement failure, the evolution will be rollback
     * to the previous legal state.
     * <p>
     * No rollback will be prompted if the provided level is
     * less or equal to the current banion level.
     * @param level the level to reach.
     * @param requirement the condition to meet.
     * @return {@code true} if evolved,
     *         {@code false} in case of rollback, or if
     *         {@code level <= current level}.
     */
    boolean evolveToLevel(int level, Predicate<Banion> requirement);

    /**
     * Prompts the {@link Banion}'s evolution to reach an adequate
     * phase based on the provided level.
     * <p>
     * By utilising a multimap, this method enables the use of
     * different requirements across various level values while accommodating
     * the shared usage of a single requirement for multiple values.
     * <p>
     * Upon any requirement failure, the evolution will be rollback
     * to the previous legal state.
     * <p>
     * This multimap must contain all level values between the {@code current
     * level} (inclusive) and the given {@code level} (exclusive).
     * @param level the level to reach.
     * @param requirements a multimap that links a specific requirement
     *                     to a list of levels.
     * @return {@code true} if evolved,
     *         {@code false} in case of rollback.
     */
    boolean evolveToLevel(int level, MultiValuedMap<Predicate<Banion>, Integer> requirements);

}
