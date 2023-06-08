package darwinsquest.core.gameobject.entity;

import darwinsquest.core.gameobject.move.BasicMove;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerTest {

    private static final String NAME_1 = "Alice";
    private static final String NAME_2 = "Bob";
    private static final int MOVE_DAMAGE = 10;
    private static final int HP = 100;
    private static final double ATK = 1.0;
    private static final double DEF = 1.0;
    private final Element neutral = new Neutral();
    private final Set<Move> moves = Set.of(new BasicMove(MOVE_DAMAGE, "1", neutral),
        new BasicMove(MOVE_DAMAGE, "2", neutral),
        new BasicMove(MOVE_DAMAGE, "3", neutral),
        new BasicMove(MOVE_DAMAGE, "4", neutral));
    private final List<Banion> banionList = new ArrayList<>(List.of(
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves),
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves),
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves)));

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
        final GameEntity p1 = new PlayerImpl(NAME_1);
        final GameEntity p2 = new PlayerImpl(NAME_2);
        assertEquals(NAME_2, p2.getName());
        assertEquals(NAME_1, p1.getName());
    }

    @Test
    void addToInventoryTest() {
        // Add collection test.
        final GameEntity p1 = new PlayerImpl(NAME_1);
        p1.addToInventory(banionList.get(0));
        p1.addToInventory(banionList.get(1));
        assertEquals(banionList.subList(0, 2), p1.getInventory());
        // Add element test.
        final GameEntity p2 = new PlayerImpl(NAME_1);
        p2.addToInventory(banionList);
        assertEquals(banionList, p2.getInventory());
    }

    @Test
    void updateInventoryTest() {
        // Updating the initial inventory to contain all fire banions.
        final GameEntity p1 = new PlayerImpl(NAME_1);
        p1.addToInventory(banionList);
        final List<Optional<Banion>> banionsRemoved = IntStream.range(1, banionList.size())
                .mapToObj(i -> p1.updateInventory(banionList.get(i), new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves)))
                .toList();
        assertTrue(p1.getInventory().stream().allMatch(b -> b.getElement().equals(neutral)));
        assertEquals(banionList.subList(1, banionList.size()),
                banionsRemoved.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
        // Same banion test.
        final GameEntity p2 = new PlayerImpl(NAME_1);
        final var b1 = banionList.get(0);
        final var b2 = banionList.get(1);
        final var b3 = banionList.get(2);
        assertTrue(p2.addToInventory(b1));
        assertFalse(p2.addToInventory(b1));
        assertTrue(p2.addToInventory(List.of(b2, b3)));
        assertFalse(p2.addToInventory(List.of(b1, b2, b3)));
        assertTrue(p2.addToInventory(List.of(b1, b2, b3, new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves))));
        // Non-existing banion to update test.
        final GameEntity p3 = new PlayerImpl(NAME_1);
        var retrievedBanion = p3.updateInventory(b1, b2);
        assertTrue(retrievedBanion.isEmpty());
        // Inventory contains newBanion test.
        p3.addToInventory(List.of(b1, b2));
        retrievedBanion = p3.updateInventory(b1, b2);
        assertTrue(retrievedBanion.isEmpty());
        // Inventory does not contain oldBanion test.
        retrievedBanion = p3.updateInventory(b3, new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves));
        assertTrue(retrievedBanion.isEmpty());
    }

    @Test
    void isOutOfBanionsTest() {
        final GameEntity p = new PlayerImpl(NAME_1);
        p.addToInventory(banionList);
        assertFalse(p.isOutOfBanions());
        for (int i = 0; i < banionList.size(); i++) {
            final var currentBanion = banionList.get(i);
            currentBanion.decreaseHp(currentBanion.getHp());
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
        final GameEntity p1 = new PlayerImpl(NAME_1);
        final GameEntity p2 = new PlayerImpl(NAME_2);
        final GameEntity p3 = new PlayerImpl(NAME_1);
        assertEquals(p1, p3);
        assertNotEquals(p1, p2);
    }

}
