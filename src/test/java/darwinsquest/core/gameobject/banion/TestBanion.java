package darwinsquest.core.gameobject.banion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.core.gameobject.move.BasicMove;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import darwinsquest.generation.BanionFactory;
import darwinsquest.generation.MoveFactory;
import darwinsquest.core.gameobject.element.Neutral;

/**
 * Test Class for {@link BanionImpl}.
 */
class TestBanion {

    private static final int DAMAGE = 10;

    private final Set<Banion> banions = new BanionFactory().createElements();

    /**
     * Tests Banion statistic changes.
     * @param zero Valid stat.
     * @param invalidHp Invalid stat.
     */
    @ParameterizedTest
    @CsvSource({"0, -1"})
    void stats(final int zero, final int invalidHp) {
        final var banion = banions.stream().findAny().get();

        // Initial banion state
        assertTrue(banions.stream().allMatch(Banion::isAlive));
        assertTrue(banions.stream().allMatch(b -> b.getMaxHp() == b.getHp()));
        assertEquals(banion.getMaxHp(), banion.getHp());

        // banion illegal actions
        assertThrows(IllegalArgumentException.class, () -> banion.increaseHp(invalidHp));
        assertThrows(IllegalArgumentException.class, () -> banion.decreaseHp(zero));

        final var initialHp = banion.getHp();

        banion.decreaseHp(initialHp);
        assertFalse(banion.isAlive());
        assertEquals(banion.getHp(), Banion.MIN_HP);

        final var deadHp = banion.getHp();

        assertTrue(initialHp > deadHp);

        banion.increaseHp(initialHp);
        assertTrue(banion.isAlive());

        assertThrows(IllegalArgumentException.class, () -> banion.setMaxHp(deadHp));
        banion.decreaseHp(initialHp);
        banion.decreaseHp(initialHp);
        assertFalse(banion.isAlive());
        IntStream.rangeClosed(BanionImpl.MIN_HP, banion.getMaxHp()).forEach(i -> {
            banion.increaseHp(1);
            assertTrue(banion.isAlive());
        });
        banion.increaseHp(1); // Banion could have MAX_HP + 1
        assertEquals(banion.getHp(), banion.getMaxHp());
    }

    @Test
    void moves() {
        final var neutral = new Neutral();
        // It's unlikely that exists an equal move in banion
        final var newMove = new BasicMove(DAMAGE, "*", neutral);

        banions.stream().map(Banion::getMoves).forEach(m -> assertEquals(m.size(), Banion.NUM_MOVES));
        final var banion = banions.stream().findAny().get();

        assertFalse(banion.getMoves().contains(newMove));
        final var moves = banion.getMoves().stream().toList();

        // trying to replace a move with a new one
        assertTrue(banion.replaceMove(moves.get(0), newMove));
        assertEquals(Banion.NUM_MOVES, banion.getMoves().size());
        assertTrue(banion.getMoves().contains(newMove));
        assertFalse(banion.getMoves().contains(moves.get(0)));

        // trying to replace a move with another yet present
        assertTrue(banion.getMoves().contains(moves.get(1)));
        assertFalse(banion.replaceMove(newMove, moves.get(1)));
        assertEquals(Banion.NUM_MOVES, banion.getMoves().size());
        assertTrue(banion.getMoves().contains(newMove));
        assertTrue(banion.getMoves().contains(moves.get(1)));

        // Checks types of each Banion move
        banions.forEach(b -> b.getMoves().stream()
            .map(Elemental::getElement)
            .forEach(e -> assertTrue(e.equals(b.getElement())
                || e.equals(neutral))));
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
        final var atk = 1.0;
        final var def = 1.0;
        final var element = new Neutral();
        final var moves = new MoveFactory().createElements().stream()
            .filter(m -> m.getElement().getClass().equals(element.getClass()))
            .limit(BanionImpl.NUM_MOVES)
            .collect(Collectors.toSet());

        final var banions = Stream.generate(() -> new BanionImpl(element, name, hp, atk, def, moves)).limit(size).toList();

        banions.forEach(b1 -> {
            banions.stream().filter(b -> b1 != b).forEach(b2 -> assertNotEquals(b1, b2));
            banions.stream().filter(b -> b1 == b).forEach(b2 -> assertEquals(b1, b2));
        });

        final var banion = new BanionImpl(element, name, hp, atk, def, moves);
        final var banionClones = Stream.generate(banion::copy).limit(size).toList();

        // Banions are considered different
        banionClones.forEach(b1 -> {
            banionClones.stream().filter(b -> b1 != b).forEach(b2 -> assertNotEquals(b1, b2));
            banionClones.stream().filter(b -> b1 == b).forEach(b2 -> assertEquals(b1, b2));
        });
    }
}
