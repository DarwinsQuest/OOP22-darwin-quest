package darwinsquest;

import java.util.Set;

/**
 * Factory of elements of generic type.
 * @param <T> the type of the generated elements.
 */
public interface TypeFactory<T> {

    /**
     * Creates elements.
     * @return the elements provided.
     */
    Set<T> createElements();
}
