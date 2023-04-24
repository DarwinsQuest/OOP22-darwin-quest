package darwinsquest.core;

import java.util.function.IntPredicate;

/**
 * Class that implements a basic {@link Banion}.
 */
public class BanionImpl implements Banion {

    private int hp;

    /**
     * Costructor that creates a simple {@link Banion} with a provided hit points amount.
     * @param hp Hit points, represents health.
     * @throws IllegalArgumentException Hit points can't be init as negative or zero.
     */
    public BanionImpl(final int hp) {
        testStat(hp, value -> value > 0, "Banion hp can't be init to a negative value or zero.");
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
}
