package darwinsquest.core;

/**
 * Interface that denotes a clonable class.
 * @param <T> the type of the cloned object.
 */
public interface Cloneable<T> {

    /**
     * Clones this {@link T} element.
     * @return the cloned copy
     */
    T copy();
}
