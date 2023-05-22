package darwinsquest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import darwinsquest.core.Banion;
import darwinsquest.core.Move;
import darwinsquest.core.element.Element;

/**
 * Class that generates {@link Move}, {@link Banion} and {@link Element}.
 */
class TestGeneration {

    private static final double NANOSEC_TO_SEC = 0.000_000_001;

    private static Set<Element> elements;
    private static Set<Move> moves;
    private static Set<Banion> banions;

    private static <T> Set<T> read(final Class<T> typeClass, final TypeFactory<T> factory) {
        final var entities = factory.createElements();

        assertTrue(entities.isPresent(),
            () -> typeClass.getSimpleName() + "s should be read correctly");

        assertFalse(entities.get().isEmpty(),
            () -> typeClass.getSimpleName() + "s should contain at least one " + typeClass.getSimpleName());

        return entities.get();
    }

    @BeforeAll
    static void readCorrectly() {
        elements = read(Element.class, new ElementFactory());
        moves = read(Move.class, new MoveFactory());
        banions = read(Banion.class, new BanionFactory());
    }

    @SuppressWarnings("PMD") // Used only as visual check
    private static <T> void print(final Class<T> typeClass, final Set<T> entities) {
        System.out.println("- " + typeClass.getSimpleName() + "s size = " + entities.size());
        entities.stream().forEach(e -> System.out.println(e));
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

        assertTrue(range < 1.0);
    }
}
