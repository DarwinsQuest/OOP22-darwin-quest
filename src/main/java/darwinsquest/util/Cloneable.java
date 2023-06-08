package darwinsquest.util;

/**
 * Interface that denotes a cloneable {@link Object}.
 * @param <T> the type of the clone result.
 */
public interface Cloneable<T> {

    /**
     * Clones this {@link T} element.
     * @return the cloned copy
     */
    T copy();
}
