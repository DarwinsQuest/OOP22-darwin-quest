package darwinsquest.core.gameobject.entity;

import darwinsquest.core.difficulty.AI;
import darwinsquest.core.difficulty.BasicAI;
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

class OpponentTest {

    private static final String NAME_1 = "Alice";
    private static final String NAME_2 = "Bob";
    private static final int MOVE_DAMAGE = 10;
    private static final AI AI = new BasicAI();
    private final Element neutral = new Neutral();
    private static final int HP = 100;
    private static final double ATK = 1.0;
    private static final double DEF = 1.0;
    private final Set<Move> moves = Set.of(new BasicMove(MOVE_DAMAGE, "9", neutral),
        new BasicMove(MOVE_DAMAGE, "2", neutral),
        new BasicMove(MOVE_DAMAGE, "19", neutral),
        new BasicMove(MOVE_DAMAGE, "4", neutral));
    private final List<Banion> banionList = new ArrayList<>(List.of(
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves),
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves),
        new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves)));

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
        final GameEntity o1 = new OpponentImpl(NAME_1, AI);
        o1.addToInventory(banionList.get(0));
        o1.addToInventory(banionList.get(1));
        assertEquals(banionList.subList(0, 2), o1.getInventory());
        // Add element test.
        final GameEntity o2 = new OpponentImpl(NAME_1, AI);
        o2.addToInventory(banionList);
        assertEquals(banionList, o2.getInventory());
    }

    @Test
    void updateInventoryTest() {
        // Updating the initial inventory to contain all fire banions.
        final GameEntity o1 = new OpponentImpl(NAME_1, AI);
        o1.addToInventory(banionList);
        final List<Optional<Banion>> banionsRemoved = IntStream.range(1, banionList.size())
                .mapToObj(i -> o1.updateInventory(banionList.get(i), new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves)))
                .toList();
        assertTrue(o1.getInventory().stream().allMatch(b -> b.getElement().equals(neutral)));
        assertEquals(banionList.subList(1, banionList.size()),
                banionsRemoved.stream()
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .toList()
        );
        // Same banion test.
        final GameEntity o2 = new OpponentImpl(NAME_1, AI);
        final var b1 = banionList.get(0);
        final var b2 = banionList.get(1);
        final var b3 = banionList.get(2);
        assertTrue(o2.addToInventory(b1));
        assertFalse(o2.addToInventory(b1));
        assertTrue(o2.addToInventory(List.of(b2, b3)));
        assertFalse(o2.addToInventory(List.of(b1, b2, b3)));
        assertTrue(o2.addToInventory(List.of(b1, b2, b3, new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves))));
        // Non-existing banion to update test.
        final GameEntity o3 = new OpponentImpl(NAME_1, AI);
        var retrievedBanion = o3.updateInventory(b1, b2);
        assertTrue(retrievedBanion.isEmpty());
        // Inventory contains newBanion test.
        o3.addToInventory(List.of(b1, b2));
        retrievedBanion = o3.updateInventory(b1, b2);
        assertTrue(retrievedBanion.isEmpty());
        // Inventory does not contain oldBanion test.
        retrievedBanion = o3.updateInventory(b3, new BanionImpl(neutral, NAME_2, HP, ATK, DEF, moves));
        assertTrue(retrievedBanion.isEmpty());
    }

    @Test
    void isOutOfBanionsTest() {
        final GameEntity o = new OpponentImpl(NAME_1, AI);
        o.addToInventory(banionList);
        assertFalse(o.isOutOfBanions());
        for (int i = 0; i < banionList.size(); i++) {
            final var currentBanion = banionList.get(i);
            currentBanion.decreaseHp(currentBanion.getHp());
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
