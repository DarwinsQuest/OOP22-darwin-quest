package darwinsquest.core;

import java.util.function.Predicate;

/**
 * A class that represents a linear {@link Evolution}.
 */
public class LinearEvolution implements Evolution {

    private static final double HP_MULTIPLIER = 15;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolve(final Banion banion, final Predicate<Banion> requirement) {
        if (!requirement.test(banion)) {
            return false;
        }
        banion.setMaxHp(increaseByPercentage(banion.getMaxHp(), HP_MULTIPLIER));
        banion.setHpToMax();
        banion.increaseLevel();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LinearEvolution{HP_MULTIPLIER=" + HP_MULTIPLIER + "%}";
    }

    private int increaseByPercentage(final int value, final double percentage) {
        final double increase = 1 + (percentage / 100);
        return (int) Math.round(value * increase);
    }

}
