package darwinsquest.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.IntPredicate;

/**
 * Class that represents a simple {@link Banion} implementation.
 */
public class BanionImpl implements Banion {

    private final String name;
    private final Collection<Move> moves;
    private int hp;

    /**
     * Costructor that creates a {@link Banion} with a provided hit points amount.
     * @param name Identifier.
     * @param hp Hit points, represents health.
     * @throws IllegalArgumentException If hit points init to negative or zero.
     */
    public BanionImpl(final String name, final int hp) {
        testStat(hp, value -> value > 0, "Banion hp can't be init to a negative value or zero.");
        moves = new HashSet<>();
        this.name = Objects.requireNonNull(name);
        this.hp = hp;
    }

    private void testStat(final int stat, final IntPredicate predicate, final String message) {
        if (predicate.negate().test(stat)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHp() {
        return hp;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If hit points set to negative.
     */
    @Override
    public void setHp(final int amount) {
        testStat(amount, value -> value >= 0, "Banion hp can't be set to a negative value.");
        hp = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Move> getMoves() {
        return Collections.unmodifiableCollection(moves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean learnMove(final Move move) {
        return moves.add(move);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean forgetMove(final Move move) {
        return moves.remove(move);
    }

    /**
     * Retireves {@link Banion} name.
     */
    @Override
    public String toString() {
        return name;
    }
}
