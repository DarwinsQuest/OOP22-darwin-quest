package darwinsquest.core;

import org.junit.jupiter.api.Test;

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
        assertThrows(IllegalArgumentException.class, () -> new PlayerImpl(""));
        assertThrows(IllegalArgumentException.class, () -> new PlayerImpl("  "));
    }

    @Test
    void nicknameGetterTest() {
        final var p1 = new PlayerImpl(NAME_1);
        final var p2 = new PlayerImpl(NAME_2);
        assertEquals(NAME_1, p1.getNickname());
        assertEquals(NAME_2, p2.getNickname());
    }

    @Test
    void isOutOfBanionsTest() {
        final var p = new PlayerImpl(NAME_1);
        final List<Banion> inventory = p.getInventoryAsList();
        inventory.add(new BanionImpl(NAME_2, HP));
        assertFalse(p.isOutOfBanions());
        inventory.get(0).setHp(0);
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
