package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

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
     * @param damage the moves damage.
     * @param hp the hit points of the {@link BanionImpl}.
     */
    @ParameterizedTest
    @CsvSource({"10, 100"})
    void moves(final int damage, final int hp) {
        final var banion = new BanionImpl(new Water(), "Black Noir", hp);
        final var moves = Set.of(new BasicMove(damage, "fireMove", new Fire()),
            new BasicMove(damage, "waterMove", new Water()),
            new BasicMove(damage, "neutralMove", new Neutral()),
            new BasicMove(damage, "airMove", new Air()));

        moves.stream().forEach(move -> banion.learnMove(move));
        assertTrue(banion.getMoves().stream()
            .collect(Collectors.toSet())
            .containsAll(moves.stream()
                .filter(move -> move.getElement().equals(banion.getElement())
                    || move.getElement().getClass().equals(Neutral.class))
                .collect(Collectors.toSet())));
    }

    /**
     * Tests {@link BanionImpl#equals}.
     * @param hp the hit points of the {@link BanionImpl}.
     */
    @ParameterizedTest
    @CsvSource({"100"})
    void comparisons(final int hp) {
        final String name1 = "1";
        final String name2 = "2";
        final var banion1 = new BanionImpl(new Water(), name1, hp);
        final var banion2 = new BanionImpl(new Water(), name1, hp);
        final var banion3 = new BanionImpl(new Fire(), name1, hp);
        final var banion4 = new BanionImpl(new Neutral(), name1, hp);
        final var banion5 = new BanionImpl(new Water(), name2, hp);

        assertEquals(banion1, banion2);
        assertNotEquals(banion1, banion3);
        assertNotEquals(banion1, banion4);
        assertNotEquals(banion4, banion5);
    }
}
