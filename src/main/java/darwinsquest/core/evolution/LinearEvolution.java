package darwinsquest.core.evolution;

import darwinsquest.core.gameobject.banion.Banion;

import java.util.function.Predicate;

/**
 * A class that represents a linear {@link Evolution}.
 */
public class LinearEvolution implements Evolution {

    private static final int MAX_HP = 30;
    private static final double HP_MULTIPLIER = 0.15;
    private static final double ATK_MULTIPLIER = 0.15;
    private static final double DEF_MULTIPLIER = 0.15;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolve(final Banion banion, final Predicate<Banion> requirement) {
        if (!requirement.test(banion)) {
            return false;
        }
        // Evolution will not prompt if the MAX_HP ceiling is already reached.
        if (banion.getHp() < MAX_HP) {
            final int increase = increaseByPercentage(banion.getMaxHp(), HP_MULTIPLIER);
            banion.setMaxHp(Math.min(increase, MAX_HP));
        }
        banion.setHpToMax();
        banion.setAttack(increaseByPercentage(banion.getAttack(), ATK_MULTIPLIER));
        banion.setDefence(increaseByPercentage(banion.getDefence(), DEF_MULTIPLIER));
        banion.increaseLevel();
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "LinearEvolution{HP_MULTIPLIER=" + HP_MULTIPLIER + "%, "
                + "ATK_MULTIPLIER=" + ATK_MULTIPLIER + "%, "
                + "DEF_MULTIPLIER=" + DEF_MULTIPLIER + "%}";
    }

    private int increaseByPercentage(final int value, final double percentage) {
        final double increase = 1 + percentage;
        return (int) Math.round(value * increase);
    }

    private double increaseByPercentage(final double value, final double percentage) {
        final double increase = 1 + percentage;
        return value * increase;
    }

}
