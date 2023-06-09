package darwinsquest.core.statistic;

/**
 * Class that represents a defence statistic.
 * @param <T> the type of the value that numerically represents the statistic.
 */
public final class DefenceStat<T extends Number> extends AbstractStatistic<T> {

    /**
     * Constructor for a defence statistic.
     * @param value the initial value.
     */
    public DefenceStat(final T value) {
        super("defence", value);
    }
}
