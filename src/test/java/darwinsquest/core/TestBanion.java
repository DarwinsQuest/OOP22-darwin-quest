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

import darwinsquest.BanionFactory;
import darwinsquest.MoveFactory;
import darwinsquest.core.element.Neutral;

/**
 * Test Class for {@link darwinsquest.core.BanionImpl}.
 */
class TestBanion {

    private final Set<Banion> banions = new BanionFactory().createElements().get();

    /**
     * Tests Banion statistic changes.
     * @param validHp Valid stat.
     * @param invalidHp Invalid stat.
     */
    @ParameterizedTest
    @CsvSource({"0, -1"})
    void stats(final int validHp, final int invalidHp) {
        final var banion = banions.stream().findAny().get();

        banion.setHp(validHp);
        assertEquals(validHp, banion.getHp());

        assertThrows(IllegalArgumentException.class, () -> banion.setHp(invalidHp), "Expected throw IllegalArgumentException");
    }

    /**
     * Tests Banion alive state.
     * @param notAliveHp Not alive amount stat.
     */
    @ParameterizedTest
    @CsvSource({"0"})
    void alive(final int notAliveHp) {
        final var banion = banions.stream().findAny().get();
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
    void numMoves(final int damage, final int hp) {
        banions.stream().map(Banion::getMoves).forEach(m -> assertEquals(m.size(), BanionImpl.NUM_MOVES));
        // Checks types of each Banion move
        banions.stream()
            .forEach(b -> b.getMoves().stream()
                .forEach(m -> assertTrue(m.getElement().equals(b.getElement()) 
                    || m.getElement().equals(new Neutral()))));
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
        final var element = new Neutral();
        final var moves = new MoveFactory().createElements().get().stream()
            .filter(m -> m.getElement().getClass().equals(element.getClass()))
            .limit(BanionImpl.NUM_MOVES)
            .collect(Collectors.toSet());

        final var banions = Stream.generate(() -> new BanionImpl(element, name, hp, moves)).limit(size).toList();

        banions.forEach(b1 -> {
            banions.stream().filter(b2 -> b1 != b2).forEach(b3 -> assertNotEquals(b1, b3));
            banions.stream().filter(b2 -> b1 == b2).forEach(b3 -> assertEquals(b1, b3));
        });

        final var banion = new BanionImpl(element, name, hp, moves);
        final var banionClones = Stream.generate(banion::copy).limit(size).toList();

        banionClones.forEach(o1 -> banionClones.forEach(o2 -> {
            assertNotSame(banion, o2);
            assertEquals(o1, o2);
        }));
    }
}
