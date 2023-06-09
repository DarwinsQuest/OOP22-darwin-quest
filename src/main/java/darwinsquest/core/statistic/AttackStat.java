package darwinsquest.core.statistic;

/**
 * Class that represents an attack statistic.
 * @param <T> the type of the value that numerically represents the statistic.
 */
public final class AttackStat<T extends Number> extends AbstractStatistic<T> {

    /**
     * Constructor for an attack statistic.
     * @param value the initial value.
     */
    public AttackStat(final T value) {
        super("attack", value);
    }

}
