package darwinsquest.util;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestCollectors {

    private static final int START = 0;
    private static final int END = 1000;

    @Test
    void immutableSet() {
        final var sequence = List.of(1, 3, 3, 4);

        final var set = sequence.stream().collect(MyCollectors.toImmutableSet(HashSet::new));

        assertThrows(UnsupportedOperationException.class, () -> set.add(0));
        assertThrows(UnsupportedOperationException.class, () -> set.remove(0));
    }

    @Test
    void orderedSet() {
        final var sequence = new LinkedList<Integer>();
        IntStream.range(START, END).forEach(sequence::add);

        final var set = sequence.stream().collect(MyCollectors.toImmutableSet(LinkedHashSet::new));

        int i = START;
        for (final int j: set) {
            assertEquals(i, j);
            i++;
        }
    }

    @Test
    void orderedUnrepeatedSet() {
        final var sequence = List.of(1, 2, 3, 4, 5, 7, 2, 2, 5, 1, 0, 23, 56);

        final var set = sequence.stream().collect(MyCollectors.toImmutableSet(LinkedHashSet::new));

        final var buffer = new HashSet<Integer>();
        int i = START;

        for (final int j: set) {
            while (buffer.contains(sequence.get(i))) {
                i++;
            }
            assertEquals(sequence.get(i), j);
            buffer.add(j);
            i++;
        }
    }
}
