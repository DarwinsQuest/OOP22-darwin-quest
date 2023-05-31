package darwinsquest.core.statistic;

import java.util.Objects;

/**
 * Abstract class that represents a generic statistic.
 * @param <T> the type of the value that numerically represents the statistic.
 */
public abstract class AbstractStatistic<T extends Number> implements Statistic<T> {

    private final String type;
    private T value;

    /**
     * Constructor for a statistic.
     * @param type the type of statistic.
     * @param value the initial value.
     */
    public AbstractStatistic(final String type, final T value) {
        this.type = Objects.requireNonNull(type);
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final T value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractStatistic<?> that = (AbstractStatistic<?>) o;
        return Objects.equals(type, that.type) && Objects.equals(value, that.value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "AbstractStatistic{"
                + "type='" + type + '\''
                + ", value=" + value + '}';
    }

}
