package darwinsquest.generation;

import java.util.Set;

import darwinsquest.core.gameobject.element.Neutral;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.element.Element;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Class that generates {@link Move}, {@link Banion} and {@link Element}.
 */
class TestGeneration {

    private static final double NANOSEC_TO_SEC = 0.000_000_001;
    private static final double MAX_READING_SEC_TIME = 0.5;

    private static final int ELEMENTS = 6;
    private static final int MOVES = 45;
    private static final int MOVES_PER_ELEMENT = 5;
    private static final int MOVES_PER_NEUTRAL = 15;
    private static final int BANIONS = 18;
    private static final int BANIONS_PER_ELEMENT = 3;

    private static Set<Element> elements;
    private static Set<Move> moves;
    private static Set<Banion> banions;

    private static <T> Set<T> read(final Class<T> typeClass, final SetFactory<T> factory, final int size) {
        final var entities = factory.createElements();

        assertFalse(entities.isEmpty(),
            () -> typeClass.getSimpleName() + "s should contain at least one " + typeClass.getSimpleName());

        assertEquals(entities.size(), size);
        return entities;
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

        if (range > MAX_READING_SEC_TIME) {
            System.err.println("Error! Your data reading time has exceeded " + MAX_READING_SEC_TIME + " s");
        }
    }
}
