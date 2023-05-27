package darwinsquest.core.world;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import darwinsquest.core.difficulty.Die;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import darwinsquest.utility.Asserts;

/**
 * Test Class for {@link darwinsquest.core.world.BoardImpl}.
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
        assertEquals(levels, board.getLevels());

        while (board.move().isPresent()) {
            pos = board.getPos();
        }
        assertEquals(levels + 1, pos);
        Asserts.match(pos, value -> value > 0, null);
    }
}
