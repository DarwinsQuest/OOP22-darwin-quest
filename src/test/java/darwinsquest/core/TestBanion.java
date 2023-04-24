package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Simple Test for {@link darwinsquest.core.BanionImpl}.
 */
class TestBanion {

    /**
     * Tests Banion statistic changes.
     * @param hp Valid stat.
     * @param validHp Valid stat.
     * @param invalidHp Invalid stat.
     */
    @ParameterizedTest
    @CsvSource({"10, 0, -1"})
    void stats(final int hp, final int validHp, final int invalidHp) {
        final var banion = new BanionImpl(hp);
        assertEquals(hp, banion.getHp());

        banion.setHp(validHp);
        assertEquals(validHp, banion.getHp());

        assertThrows(IllegalArgumentException.class, () -> banion.setHp(invalidHp), "Espected throw IllegalArgumentException");
    }

    /**
     * Tests Banion alive state.
     * @param aliveHp Alive amount stat.
     * @param notAliveHp Not alive amount stat.
     */
    @ParameterizedTest
    @CsvSource({"1, 0"})
    void alive(final int aliveHp, final int notAliveHp) {
        final var banion = new BanionImpl(aliveHp);
        assertTrue(banion::isAlive, "Banion should be alive with " + banion.getHp() + " of life");

        banion.setHp(notAliveHp);
        assertFalse(banion::isAlive, "Banion should not be alive with " + banion.getHp() + " of life");
    }
}
