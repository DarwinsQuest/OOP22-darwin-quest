package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import darwinsquest.core.element.Air;
import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Neutral;
import darwinsquest.core.element.Water;

/**
 * Test Class for {@link darwinsquest.core.BanionImpl}.
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

        moves.stream().forEach(banion::learnMove);

        assertTrue(banion.getMoves().stream()
            .collect(Collectors.toSet())
            .containsAll(moves.stream()
                .filter(move -> move.getElement().equals(banion.getElement())
                    || move.getElement().getClass().equals(Neutral.class))
                .collect(Collectors.toSet())));
    }

    /**
     * Tests {@link BanionImpl#equals}.
     * @param size the number of {@link BanionImpl} to create.
     */
    @ParameterizedTest
    @ValueSource(ints = {10, 100, 1_000})
    void comparisons(final long size) {
        final var name = "Translucent";
        final var hp = 100;
        final var element = new Fire();

        final var banions = Stream.generate(() -> new BanionImpl(element, name, hp)).limit(size).toList();

        banions.forEach(b1 -> {
            banions.stream().filter(b2 -> b1 != b2).forEach(b3 -> assertNotEquals(b1, b3));
            banions.stream().filter(b2 -> b1 == b2).forEach(b3 -> assertEquals(b1, b3));
        });

        final var banion = new BanionImpl(element, name, hp);
        final var identifiables = Stream.generate(banion::copy).limit(size).toList();

        identifiables.forEach(o1 -> identifiables.forEach(o2 -> {
            assertNotSame(banion, o2);
            assertEquals(o1, o2);
        }));
    }
}
