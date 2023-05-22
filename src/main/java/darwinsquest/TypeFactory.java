package darwinsquest;

import java.util.Optional;
import java.util.Set;

/**
 * Factory of elements of generic type.
 * @param <T> the type of the generated elements.
 */
public interface TypeFactory<T> {

    /**
     * Creates elements.
     * @return the elements provided, {@code Optional#empty()} if error occurred.
     */
    Optional<Set<T>> createElements();
}
