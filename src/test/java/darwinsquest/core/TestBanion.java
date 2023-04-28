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
import darwinsquest.core.element.Element;
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
     * A {@link Move} implementation for tests only.
     */
    static final class TestMove implements Move {

        private final String name;
        private final Element element;

        /**
         * Creates a test move.
         * @param name the name.
         * @param element the element of affinity.
         */
        TestMove(final String name, final Element element) {
            this.name = name;
            this.element = element;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Element getElement() {
            return element;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void perform(final Banion banion) {
            throw new UnsupportedOperationException("Unimplemented method 'perform'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isStackable() {
            throw new UnsupportedOperationException("Unimplemented method 'isStackable'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getCooldown() {
            throw new UnsupportedOperationException("Unimplemented method 'getCooldown'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int getDuration() {
            throw new UnsupportedOperationException("Unimplemented method 'getDuration'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getType() {
            throw new UnsupportedOperationException("Unimplemented method 'getType'");
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /**
     * Tests possibility to add {@link Move} to a {@link BanionImpl}.
     */
    @Test
    void moves() {
        final var banion = new BanionImpl(new Water(), "Black Noir", 1);
        final var moves = Set.of(new TestMove("FireMove", new Fire()),
            new TestMove("waterMove", new Water()),
            new TestMove("neutralMove", new Neutral()),
            new TestMove("airMove", new Air()));

        moves.stream().forEach(move -> banion.learnMove(move));
        assertTrue(banion.getMoves().stream()
            .collect(Collectors.toSet())
            .containsAll(moves.stream()
                .filter(move -> move.getElement().equals(banion.getElement())
                    || move.getElement().getClass().equals(Neutral.class))
                .collect(Collectors.toSet())));
    }
}
