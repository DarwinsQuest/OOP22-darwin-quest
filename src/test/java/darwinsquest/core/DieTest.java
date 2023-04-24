package darwinsquest.core;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.*;

public class DieTest {

    private final Die d1 = new Die();
    private final Die d2 = new Die(4);
    private final Die d3 = new Die(6);
    private final Die d4 = new Die(4);

    @Test
    void dieEquality() {
        assertNotEquals(d1, d2);
        assertEquals(d1, d3);
        assertEquals(d2, d4);
        assertNotEquals(d3, d4);
    }

    @Test
    void dieGetFaces() {
        assertEquals(d1.getFaces(), 6);
        assertEquals(d2.getFaces(), 4);
        assertEquals(d3.getFaces(), 6);
        assertEquals(d4.getFaces(), 4);
    }

    @Test
    void dieGetAsInt() {
        final List<Die> dieList = new ArrayList<>(List.of(d1, d2, d3, d4));
        BiConsumer<Die, Integer> assertLoop = (d, i) -> {
            for (int j = 0; j < i; j++) {
                assertTrue(d.getAsInt() <= d.getFaces());
            }
        };
        dieList.forEach(d -> assertLoop.accept(d, 10));
    }

}