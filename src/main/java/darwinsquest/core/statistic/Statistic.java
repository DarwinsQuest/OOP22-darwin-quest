package darwinsquest.core.statistic;

/**
 * Interface that represents a {@link darwinsquest.core.gameobject.banion.Banion}'s
 * statistic.
 * @param <T> the type of the value that numerically represents the statistic.
 */
public interface Statistic<T extends Number> {

    /**
     * Setter of the statistic.
     * @param value the stat value.
     */
    void setValue(T value);

    /**
     * Getter for the statistic.
     * @return the stat's value.
     */
    T getValue();

}
