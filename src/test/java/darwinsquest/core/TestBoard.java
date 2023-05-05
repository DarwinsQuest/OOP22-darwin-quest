package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import darwinsquest.core.utility.Assert;

/**
 * Simple Test for {@link darwinsquest.core.BoardImpl}.
 */
class TestBoard {

    @Test
    void assertions() {
        assertThrows(IllegalArgumentException.class, () -> new BoardImpl(0, () -> 0));
    }

    @ParameterizedTest
    @CsvSource({"10"})
    void movement(final int levels) {
        final var board = new BoardImpl(levels, new Die());
        int pos = 0;

        while (board.move().isPresent()) {
            pos = board.getPos();
        }
        assertEquals(levels + 1, pos);
        Assert.match(pos, value -> value > 0, null);
    }
}
