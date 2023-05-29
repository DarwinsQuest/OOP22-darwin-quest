package darwinsquest.util;

import java.util.function.IntPredicate;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;

/**
 * Class that represents a general Utility to assert certain conditions.
 */
public final class Asserts {

    private Asserts() { }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param condition the predicate to match.
     * @param <T> the type of the value to be checked.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static <T> T match(final T value, final Predicate<T> condition) {
        return match(value, condition, null);
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param condition the predicate to match.
     * @param message the message to show in case of outmatching.
     * @param <T> the type of the value to be checked.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static <T> T match(final T value, final Predicate<T> condition, final String message) {
        if (condition.negate().test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}.
     * @param value the value to verify.
     * @param condition the predicate to match.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static int intMatch(final int value, final IntPredicate condition) {
        return intMatch(value, condition, null);
    }

    /**
     * Asserts that a certain {@code value} match a specific {@code predicate}, otherwise shows a {@code message}.
     * @param value the value to verify.
     * @param condition the predicate to match.
     * @param message the message to show in case of outmatching.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException when {@code predicate} isn't matched.
     */
    public static int intMatch(final int value, final IntPredicate condition, final String message) {
        if (condition.negate().test(value)) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    /**
     * Asserts that a certain string is not {@code null} or empty.
     * @param value the string that has to match the condition.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException in case of outmatching.
     */
    public static String stringNotNullOrWhiteSpace(final String value) {
        return stringNotNullOrWhiteSpace(value, null);
    }

    /**
     * Asserts that a certain string is not {@code null} or empty, otherwise shows a {@code message}.
     * @param value the string that has to match the condition.
     * @param message the message to show in case of outmatching.
     * @return the provided {@code value}.
     * @throws IllegalArgumentException in case of outmatching.
     */
    public static String stringNotNullOrWhiteSpace(final String value, final String message) {
        return match(value, StringUtils::isNotBlank, message);
    }
}
