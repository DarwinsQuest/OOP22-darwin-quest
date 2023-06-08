package darwinsquest.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Test Class for {@link darwinsquest.util.Asserts}.
 */
class TestAssert {

    @Test
    void strings() {
        assertThrows(IllegalArgumentException.class, () -> Asserts.stringNotNullOrWhiteSpace(null));

        List.of("", " ", "  ").forEach(s -> {
            assertThrows(IllegalArgumentException.class, () -> Asserts.stringNotNullOrWhiteSpace(s));
        });

        List.of(" a ", " . ", " 5 ", "null").forEach(s -> {
            Asserts.stringNotNullOrWhiteSpace(s);
        });
    }

    @Test
    void ints() {
        final int max = 1001;
        IntStream.range(0, max).forEach(i -> {
            assertThrows(IllegalArgumentException.class, () -> Asserts.intMatch(i, v -> -v > 0 || v < 0));
        });
    }

    @Test
    void values() {
        assertThrows(IllegalArgumentException.class, () -> Asserts.match(null, Objects::nonNull));
    }
}
