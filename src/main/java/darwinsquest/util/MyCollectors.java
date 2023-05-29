package darwinsquest.util;

import java.util.Collections;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Class that represents a general Utility to assert certain conditions.
 */
public final class MyCollectors {

    private MyCollectors() {

    }

    /**
     * Collects data into an immutable {@link Set}.
     * @param supplier the provider of the {@link Set} implementation to wrap.
     * @return the immutable wrapper for the {@link Set} provided.
     * @param <T> the type of elements in the {@link Set}.
     * @param <A> the type of the generated immutable {@link Set}.
     */
    public static <T, A extends Set<T>> Collector<T, A, Set<T>> toImmutableSet(final Supplier<A> supplier) {
        return Collector.of(supplier,
            Set::add,
            (left, right) -> {
                left.addAll(right);
                return left;
            },
            Collections::unmodifiableSet);
    }
}
