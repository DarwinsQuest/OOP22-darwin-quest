package darwinsquesttest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import darwinsquest.core.BanionImpl;

/**
 * Simple Test for {@link darwinsquest.core.BanionImpl}.
 */
class TestBanion {

    /**
     * Tests Banion statistic changes.
     * @param hp Valid stat.
     * @param atk Valid stat.
     * @param def Valid stat.
     * @param validHp Valid stat.
     * @param validAtk Valid stat.
     * @param validDef Valid stat.
     * @param invalidHp Invalid stat.
     * @param invalidAtk Invalid stat.
     * @param invalidDef Invalid stat.
     */
    @ParameterizedTest
    @CsvSource({"10, 100, 1000, 0, 1, 1, -1, 0, 0"})
    void stats(final int hp, final int atk, final int def,
        final int validHp, final int validAtk, final int validDef,
        final int invalidHp, final int invalidAtk, final int invalidDef) {

        final var banion = new BanionImpl(hp, atk, def);

        assertEquals(hp, banion.getHp());
        assertEquals(atk, banion.getAtk());
        assertEquals(def, banion.getDef());

        banion.setHp(validHp);
        banion.setAtk(validAtk);
        banion.setDef(validDef);

        assertEquals(validHp, banion.getHp());
        assertEquals(validAtk, banion.getAtk());
        assertEquals(validDef, banion.getAtk());

        final String espected = "Espected throw IllegalArgumentException";
        final var exc = IllegalArgumentException.class;
        assertThrows(exc, () -> banion.setHp(invalidHp), espected);
        assertThrows(exc, () -> banion.setAtk(invalidAtk), espected);
        assertThrows(exc, () -> banion.setDef(invalidDef), espected);
    }

    /**
     * Tests Banion alive state.
     * @param aliveHp Alive amount stat.
     * @param notAliveHp Not alive amount stat.
     */
    @ParameterizedTest
    @CsvSource({"1, 0"})
    void alive(final int aliveHp, final int notAliveHp) {
        final var banion = new BanionImpl(aliveHp, 1, 1);
        assertTrue(banion::isAlive, "Banion should be alive with " + banion.getHp() + " of life");

        banion.setHp(notAliveHp);
        assertFalse(banion::isAlive, "Banion should not be alive with " + banion.getHp() + " of life");
    }
}
