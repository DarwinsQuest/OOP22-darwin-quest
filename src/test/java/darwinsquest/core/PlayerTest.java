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

class PlayerTest {

    private static final String NAME_1 = "Alice";
    private static final String NAME_2 = "Bob";
    private static final int HP = 100;
    private final List<Banion> banionList = new ArrayList<>(List.of(
            new BanionImpl(new Fire(), NAME_2, HP),
            new BanionImpl(new Water(), NAME_2, HP),
            new BanionImpl(new Grass(), NAME_2, HP))
    );

    @Test
    void createPlayerTest() {
        assertThrows(IllegalArgumentException.class, () -> new PlayerImpl(null));
        final List<String> stringsToCheck = new ArrayList<>(List.of(
                "",
                "  ",
                "123",
                "1" + NAME_1,
                "!" + NAME_2,
                "@@foo",
                "bar%",
                "€uro",
                "_a",
                "b_",
                "__c_",
                "d__",
                "Amélie",
                "Walter White",
                "JessePinkman ",
                "魚")
        );
        stringsToCheck.forEach(s -> assertThrows(IllegalArgumentException.class, () -> new PlayerImpl(s)));
    }

    @Test
    void nicknameGetterTest() {
        final Entity p1 = new PlayerImpl(NAME_1);
        final Entity p2 = new PlayerImpl(NAME_2);
        assertEquals(NAME_2, p2.getName());
        assertEquals(NAME_1, p1.getName());
    }

    @Test
    void addToInventoryTest() {
        // Add collection test.
        final Entity p1 = new PlayerImpl(NAME_1);
        p1.addToInventory(banionList.get(0));
        p1.addToInventory(banionList.get(1));
        assertEquals(banionList.subList(0, 2), p1.getInventory());
        // Add element test.
        final Entity p2 = new PlayerImpl(NAME_1);
        p2.addToInventory(banionList);
        assertEquals(banionList, p2.getInventory());
    }

    @Test
    void updateInventoryTest() {
        // Updating the initial inventory to contain all fire banions.
        final Entity p1 = new PlayerImpl(NAME_1);
        p1.addToInventory(banionList);
        final List<Optional<Banion>> banionsRemoved = IntStream.range(1, banionList.size())
                .mapToObj(i -> p1.updateInventory(banionList.get(i), new BanionImpl(new Fire(), NAME_2, HP)))
                .toList();
        assertTrue(p1.getInventory().stream().allMatch(b -> b.getElement().equals(new Fire())));
        assertEquals(banionList.subList(1, banionList.size()),
                banionsRemoved.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
        // Same banion instances test.
        final Entity p2 = new PlayerImpl(NAME_1);
        final var b1 = banionList.get(0);
        final var b2 = banionList.get(1);
        final var b3 = banionList.get(2);
        assertTrue(p2.addToInventory(b1));
        assertFalse(p2.addToInventory(b1));
        assertTrue(p2.addToInventory(List.of(b2, b3)));
        assertFalse(p2.addToInventory(List.of(b1, b2, b3)));
        assertTrue(p2.addToInventory(List.of(b1, b2, b3, new BanionImpl(new Air(), NAME_2, HP))));
        // Not existing banion to update test.
        final Entity p3 = new PlayerImpl(NAME_1);
        final var testBanion = p3.updateInventory(banionList.get(0), banionList.get(1));
        assertTrue(testBanion.isEmpty());
    }

    @Test
    void isOutOfBanionsTest() {
        final Entity p = new PlayerImpl(NAME_1);
        p.addToInventory(banionList);
        assertFalse(p.isOutOfBanions());
        for (int i = 0; i < banionList.size(); i++) {
            final var currentBanion = banionList.get(i);
            currentBanion.setHp(0);
            p.updateInventory(currentBanion, currentBanion);
            if (i == banionList.size() - 1) {
                assertTrue(p.isOutOfBanions());
            } else {
                assertFalse(p.isOutOfBanions());
            }
        }
    }

    @Test
    void equalityTest() {
        final Entity p1 = new PlayerImpl(NAME_1);
        final Entity p2 = new PlayerImpl(NAME_2);
        final Entity p3 = new PlayerImpl(NAME_1);
        assertEquals(p1, p3);
        assertNotEquals(p1, p2);
    }

}
