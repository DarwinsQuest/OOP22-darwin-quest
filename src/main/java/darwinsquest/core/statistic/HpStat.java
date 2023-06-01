package darwinsquest.core.statistic;

/**
 * Class that represents a health points (HP) statistic.
 * @param <T> the type of the value that numerically represents the statistic.
 */
public final class HpStat<T extends Number> extends AbstractStatistic<T> {

    /**
     * Constructor for a health point (HP) statistic.
     * @param value the initial value.
     */
    public HpStat(final T value) {
        super("hp", value);
    }

}
