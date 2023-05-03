package darwinsquest.core;

import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Grass;
import darwinsquest.core.element.Water;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OpponentTest {

    private static final String NAME_1 = "Alice";
    private static final String NAME_2 = "Bob";
    private static final AI AI = new BasicAI();

    private static final int HP = 100;

    @Test
    void createOpponentTest() {
        assertThrows(IllegalArgumentException.class, () -> new OpponentImpl(null, AI));
        final List<String> stringsToCheck = new ArrayList<>(List.of("",  "  "));
        stringsToCheck.forEach(s -> assertThrows(IllegalArgumentException.class, () -> new OpponentImpl(s, AI)));
        assertThrows(NullPointerException.class, () -> new OpponentImpl(NAME_1, null));
    }

    @Test
    void nicknameGetterTest() {
        final var o1 = new OpponentImpl(NAME_1, AI);
        final var o2 = new OpponentImpl(NAME_2, AI);
        assertEquals(NAME_2, o2.getName());
        assertEquals(NAME_1, o1.getName());
    }

    @Test
    void isOutOfBanionsTest() {
        final var o = new OpponentImpl(NAME_1, AI);
        final List<Banion> banionList = new ArrayList<>(List.of(
                new BanionImpl(new Fire(), NAME_2, HP),
                new BanionImpl(new Water(), NAME_2, HP),
                new BanionImpl(new Grass(), NAME_2, HP))
        );
        IntStream.range(0, banionList.size()).forEach(i -> o.updateInventory(i, banionList.get(i)));
        assertFalse(o.isOutOfBanions());
        final var inventoryCopy = o.getInventory();
        for (int i = 0; i < banionList.size(); i++) {
            final var currentBanion = banionList.get(i);
            currentBanion.setHp(0);
            o.updateInventory(inventoryCopy.indexOf(currentBanion), currentBanion);
            if (i == banionList.size() - 1) {
                assertTrue(o.isOutOfBanions());
            } else {
                assertFalse(o.isOutOfBanions());
            }
        }
    }

    @Test
    void equalityTest() {
        final var o1 = new OpponentImpl(NAME_1, AI);
        final var o2 = new OpponentImpl(NAME_2, AI);
        final var o3 = new OpponentImpl(NAME_1, AI);
        assertEquals(o1, o3);
        assertNotEquals(o1, o2);
    }

}
