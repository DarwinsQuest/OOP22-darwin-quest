package darwinsquest.core;

import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Water;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    private static final String NAME_1 = "Alice";
    private static final String NAME_2 = "Bob";
    private static final int HP = 100;

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
        final var p1 = new PlayerImpl(NAME_1);
        final var p2 = new PlayerImpl(NAME_2);
        assertEquals(NAME_2, p2.getName());
        assertEquals(NAME_1, p1.getName());
    }

    @Test
    void isOutOfBanionsTest() {
        final var p = new PlayerImpl(NAME_1);
        final var b1 = new BanionImpl(new Fire(), NAME_2, HP);
        final var b2 = new BanionImpl(new Water(), NAME_2, HP);
        p.updateInventory(0, b1);
        p.updateInventory(1, b2);
        assertFalse(p.isOutOfBanions());
        final var inventoryCopy = p.getInventory();
        b1.setHp(0);
        p.updateInventory(inventoryCopy.indexOf(b1), b1);
        assertFalse(p.isOutOfBanions());
        b2.setHp(0);
        p.updateInventory(inventoryCopy.indexOf(b2), b2);
        assertTrue(p.isOutOfBanions());
    }

    @Test
    void equalityTest() {
        final var p1 = new PlayerImpl(NAME_1);
        final var p2 = new PlayerImpl(NAME_2);
        final var p3 = new PlayerImpl(NAME_1);
        assertEquals(p1, p3);
        assertNotEquals(p1, p2);
    }

}
