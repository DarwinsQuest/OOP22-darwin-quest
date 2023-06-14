package darwinsquest.core.world;

import darwinsquest.core.difficulty.Die;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DieTest {

    private static final int FACES_4 = 4;
    private static final int FACES_6 = 6;
    private final Die d1 = new Die();
    private final Die d2 = new Die(FACES_4);
    private final Die d3 = new Die(FACES_6);
    private final Die d4 = new Die(FACES_4);

    @Test
    void dieEquality() {
        assertNotEquals(d1, d2);
        assertEquals(d1, d3);
        assertEquals(d2, d4);
        assertNotEquals(d3, d4);
    }

    @Test
    void dieGetFaces() {
        assertEquals(d1.getFaces(), FACES_6);
        assertEquals(d2.getFaces(), FACES_4);
        assertEquals(d3.getFaces(), FACES_6);
        assertEquals(d4.getFaces(), FACES_4);
    }

    @Test
    void dieGetAsInt() {
        final List<Die> dieList = new ArrayList<>(List.of(d1, d2, d3, d4));
        final BiConsumer<Die, Integer> assertLoop = (d, i) -> {
            for (int j = 0; j < i; j++) {
                assertTrue(d.getAsInt() <= d.getFaces());
            }
        };
        dieList.forEach(d -> assertLoop.accept(d, 10));
    }

}
