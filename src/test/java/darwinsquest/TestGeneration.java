package darwinsquest;

import java.util.Set;

import darwinsquest.core.element.Neutral;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import darwinsquest.core.Banion;
import darwinsquest.core.Move;
import darwinsquest.core.element.Element;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that generates {@link Move}, {@link Banion} and {@link Element}.
 */
class TestGeneration {

    private static final double NANOSEC_TO_SEC = 0.000_000_001;

    private static final int ELEMENTS = 6;
    private static final int MOVES = 45;
    private static final int MOVES_PER_ELEMENT = 5;
    private static final int MOVES_PER_NEUTRAL = 15;
    private static final int BANIONS = 18;
    private static final int BANIONS_PER_ELEMENT = 3;

    private static Set<Element> elements;
    private static Set<Move> moves;
    private static Set<Banion> banions;

    private static <T> Set<T> read(final Class<T> typeClass, final TypeFactory<T> factory, final int size) {
        final var entities = factory.createElements();

        assertTrue(entities.isPresent(),
            () -> typeClass.getSimpleName() + "s should be read correctly");

        assertFalse(entities.get().isEmpty(),
            () -> typeClass.getSimpleName() + "s should contain at least one " + typeClass.getSimpleName());

        assertEquals(entities.get().size(), size);
        return entities.get();
    }

    @BeforeAll
    static void readCorrectly() {
        elements = read(Element.class, new ElementFactory(), ELEMENTS);
        moves = read(Move.class, new MoveFactory(), MOVES);
        elements.forEach(e -> assertEquals(moves.stream().filter(m -> m.getElement().equals(e)).count(), MOVES_PER_ELEMENT));
        assertEquals(moves.stream().filter(m -> m.getElement().equals(new Neutral())).count(), MOVES_PER_NEUTRAL);
        banions = read(Banion.class, new BanionFactory(), BANIONS);
        elements.forEach(e -> assertEquals(banions.stream().filter(m -> m.getElement().equals(e)).count(), BANIONS_PER_ELEMENT));
    }

    @SuppressWarnings("PMD") // Used only as visual check
    private static <T> void print(final Class<T> typeClass, final Set<T> entities) {
        System.out.println("- " + typeClass.getSimpleName() + "s size = " + entities.size());
        entities.forEach(System.out::println);
    }

    @SuppressWarnings("PMD") // Used only as general information
    @Test
    void performance() {
        final var start = System.nanoTime();

        print(Element.class, elements);
        System.out.println();
        print(Move.class, moves);
        System.out.println();
        print(Banion.class, banions);

        final var end = System.nanoTime();
        final var range = (end - start) * NANOSEC_TO_SEC;
        System.out.println("Time = " + range);

        assertTrue(range < 0.5);
    }
}
