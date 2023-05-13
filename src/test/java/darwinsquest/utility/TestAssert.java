package darwinsquest.utility;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

/**
 * Test Class for {@link darwinsquest.utility.Assert}.
 */
class TestAssert {

    @Test
    void strings() {
        assertThrows(IllegalArgumentException.class, () -> Assert.stringNotNullOrWhiteSpace(null));

        List.of("", " ", "  ").forEach(s -> {
            assertThrows(IllegalArgumentException.class, () -> Assert.stringNotNullOrWhiteSpace(s));
        });

        List.of(" a ", " . ", " 5 ", "null").forEach(s -> {
            Assert.stringNotNullOrWhiteSpace(s);
        });
    }

    @Test
    void ints() {
        final int max = 1001;
        IntStream.range(0, max).forEach(i -> {
            assertThrows(IllegalArgumentException.class, () -> Assert.intMatch(i, v -> -v > 0 || v < 0));
        });
    }

    @Test
    void values() {
        assertThrows(IllegalArgumentException.class, () -> Assert.match(null, Objects::nonNull));
    }
}
