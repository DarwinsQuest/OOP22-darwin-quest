package darwinsquest.core;

import darwinsquest.core.element.Air;
import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Grass;
import darwinsquest.core.element.Water;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    private final List<Banion> banionList = new ArrayList<>(List.of(
            new BanionImpl(new Fire(), NAME_2, HP),
            new BanionImpl(new Water(), NAME_2, HP),
            new BanionImpl(new Grass(), NAME_2, HP))
    );

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
    void addToInventoryTest() {
        // Add collection test.
        final Entity o1 = new OpponentImpl(NAME_1, AI);
        o1.addToInventory(banionList.get(0));
        o1.addToInventory(banionList.get(1));
        assertEquals(banionList.subList(0, 2), o1.getInventory());
        // Add element test.
        final Entity o2 = new OpponentImpl(NAME_1, AI);
        o2.addToInventory(banionList);
        assertEquals(banionList, o2.getInventory());
    }

    @Test
    void updateInventoryTest() {
        // Updating the initial inventory to contain all fire banions.
        final Entity o1 = new OpponentImpl(NAME_1, AI);
        o1.addToInventory(banionList);
        final List<Optional<Banion>> banionsRemoved = IntStream.range(1, banionList.size())
                .mapToObj(i -> o1.updateInventory(banionList.get(i), new BanionImpl(new Fire(), NAME_2, HP)))
                .toList();
        assertTrue(o1.getInventory().stream().allMatch(b -> b.getElement().equals(new Fire())));
        assertEquals(banionList.subList(1, banionList.size()),
                banionsRemoved.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
        // Same banion instances test.
        final Entity o2 = new OpponentImpl(NAME_1, AI);
        final var b1 = banionList.get(0);
        final var b2 = banionList.get(1);
        final var b3 = banionList.get(2);
        assertTrue(o2.addToInventory(b1));
        assertFalse(o2.addToInventory(b1));
        assertTrue(o2.addToInventory(List.of(b2, b3)));
        assertFalse(o2.addToInventory(List.of(b1, b2, b3)));
        assertTrue(o2.addToInventory(List.of(b1, b2, b3, new BanionImpl(new Air(), NAME_2, HP))));
        // Not existing banion to update test.
        final Entity o3 = new OpponentImpl(NAME_1, AI);
        final var testBanion = o3.updateInventory(banionList.get(0), banionList.get(1));
        assertTrue(testBanion.isEmpty());
    }

    @Test
    void isOutOfBanionsTest() {
        final Entity o = new OpponentImpl(NAME_1, AI);
        o.addToInventory(banionList);
        assertFalse(o.isOutOfBanions());
        for (int i = 0; i < banionList.size(); i++) {
            final var currentBanion = banionList.get(i);
            currentBanion.setHp(0);
            o.updateInventory(currentBanion, currentBanion);
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
