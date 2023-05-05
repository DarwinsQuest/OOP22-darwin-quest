package darwinsquest.core.utility;

import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Class that represents a general Utility to assert certain conditions.
 */
public final class Assert {

    private Assert() {

    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param predicate the predicate to match.
     * @param <T> the type of the value to be checked.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static <T> T match(final T value, final Predicate<T> predicate) {
        return match(value, predicate, null);
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param predicate the predicate to match.
     * @param message the message to show in case of unmatching.
     * @param <T> the type of the value to be checked.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static <T> T match(final T value, final Predicate<T> predicate, final String message) {
        if (predicate.negate().test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param predicate the predicate to match.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static int intMatch(final int value, final IntPredicate predicate) {
        return intMatch(value, predicate, null);
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}, otherwise shows a {@code message}.
     * @param value the value to verify.
     * @param predicate the predicate to match.
     * @param message the message to show in case of unmatching.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static int intMatch(final int value, final IntPredicate predicate, final String message) {
        if (predicate.negate().test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Asserts that a certain string is not {@code null} or empty.
     * @param value the string that has to match the condition.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException in case of unmatching.
     */
    public static String stringNotNullOrEmpty(final String value) {
        return stringNotNullOrEmpty(value, null);
    }

    /**
     * Asserts that a certain string is not {@code null} or empty, otherwise shows a {@code message}.
     * @param value the string that has to match the condition.
     * @param message the message to show in case of unmatching.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException in case of unmatching.
     */
    public static String stringNotNullOrEmpty(final String value, final String message) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}
