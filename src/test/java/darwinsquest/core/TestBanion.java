package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import darwinsquest.core.element.Air;
import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Neutral;
import darwinsquest.core.element.Water;

/**
 * Simple Test for {@link darwinsquest.core.BanionImpl}.
 */
class TestBanion {

    private static final int MOVE_DAMAGE = 10;

    /**
     * Tests Banion statistic changes.
     * @param hp Valid stat.
     * @param validHp Valid stat.
     * @param invalidHp Invalid stat.
     */
    @ParameterizedTest
    @CsvSource({"10, 0, -1"})
    void stats(final int hp, final int validHp, final int invalidHp) {
        final var banion = new BanionImpl(new Fire(), "Homelander", hp);
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
        final var banion = new BanionImpl(new Water(), "Queen Maeve", aliveHp);
        assertTrue(banion::isAlive, "Banion should be alive with " + banion.getHp() + " of life");

        banion.setHp(notAliveHp);
        assertFalse(banion::isAlive, "Banion should not be alive with " + banion.getHp() + " of life");
    }

    /**
     * Tests possibility to add {@link Move} to a {@link BanionImpl}.
     */
    @Test
    void moves() {
        final var banion = new BanionImpl(new Water(), "Black Noir", 1);
        final var moves = Set.of(new BasicMove(MOVE_DAMAGE, "fireMove", new Fire()),
            new BasicMove(MOVE_DAMAGE, "waterMove", new Water()),
            new BasicMove(MOVE_DAMAGE, "neutralMove", new Neutral()),
            new BasicMove(MOVE_DAMAGE, "airMove", new Air()));

        moves.stream().forEach(move -> banion.learnMove(move));
        assertTrue(banion.getMoves().stream()
            .collect(Collectors.toSet())
            .containsAll(moves.stream()
                .filter(move -> move.getElement().equals(banion.getElement())
                    || move.getElement().getClass().equals(Neutral.class))
                .collect(Collectors.toSet())));
    }
}
