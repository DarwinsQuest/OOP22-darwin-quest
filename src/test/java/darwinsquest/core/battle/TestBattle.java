package darwinsquest.core.battle;

import darwinsquest.core.battle.turn.MoveTurn;
import darwinsquest.core.battle.turn.SwapTurn;
import darwinsquest.core.difficulty.BasicAI;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.element.Neutral;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import darwinsquest.core.gameobject.move.BasicMove;
import darwinsquest.core.gameobject.move.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestBattle {

    private static final int DAMAGE_1 = 10;
    private static final int DAMAGE_2 = 15;
    private static final int HP_1 = 20;
    private static final int HP_2 = 25;
    private static final int HP_3 = 30;
    private static final int HP_4 = 35;
    private static final GameEntity E1 = new OpponentImpl("E1",
        new BasicAI());
    private static final GameEntity E2 = new OpponentImpl("E2",
        new BasicAI());

    @BeforeEach
    void addBanions() {
        final var neutralElement = new Neutral();
        final Set<Move> b1Moves = Set.of(new BasicMove(DAMAGE_1, "m1", neutralElement),
                new BasicMove(DAMAGE_2, "m2", neutralElement),
                new BasicMove(DAMAGE_1, "m3", neutralElement),
                new BasicMove(DAMAGE_2, "m4", neutralElement));
        final Set<Move> b2Moves = Set.of(new BasicMove(DAMAGE_1, "m1", neutralElement),
                new BasicMove(DAMAGE_2, "m2", neutralElement),
                new BasicMove(DAMAGE_1, "m3", neutralElement),
                new BasicMove(DAMAGE_2, "m4", neutralElement));
        final Set<Move> b3Moves = Set.of(new BasicMove(DAMAGE_1, "m1", neutralElement),
                new BasicMove(DAMAGE_2, "m2", neutralElement),
                new BasicMove(DAMAGE_1, "m3", neutralElement),
                new BasicMove(DAMAGE_2, "m4", neutralElement));
        final Set<Move> b4Moves = Set.of(new BasicMove(DAMAGE_1, "m1", neutralElement),
                new BasicMove(DAMAGE_2, "m2", neutralElement),
                new BasicMove(DAMAGE_1, "m3", neutralElement),
                new BasicMove(DAMAGE_2, "m4", neutralElement));
        final var b1 = new BanionImpl(neutralElement, "b1", HP_1, b1Moves);
        final var b2 = new BanionImpl(neutralElement, "b2", HP_2, b2Moves);
        final var b3 = new BanionImpl(neutralElement, "b3", HP_3, b3Moves);
        final var b4 = new BanionImpl(neutralElement, "b4", HP_4, b4Moves);
        E1.addToInventory(b1);
        E1.addToInventory(b2);
        E2.addToInventory(b3);
        E2.addToInventory(b4);
    }

    @Test
    void testCreation() {
        assertThrows(NullPointerException.class, () -> new BasicBattleTile(null, null));
        assertThrows(NullPointerException.class, () -> new BasicBattleTile(E1, null));
        assertThrows(NullPointerException.class, () -> new BasicBattleTile(null, E2));
    }

    @Test
    void testEquality() {
        assertEquals(new BasicBattleTile(E1, E2), new BasicBattleTile(E1, E2));
        assertNotEquals(new BasicBattleTile(E1, E2), new BasicBattleTile(E2, E1));
    }

    @Test
    void testGetters() {
        final var battle = new BasicBattleTile(E1, E2);
        assertEquals(E1, battle.getPlayer());
        assertEquals(E2, battle.getOpponent());
    }

    @Test
    void testBattleStart() {
        final var battle = new BasicBattleTile(E2, E1);
        battle.startBattle();
        if (battle.isWinner(E2)) {
            assertThrows(IllegalStateException.class, battle::startBattle);
        } else {
            assertDoesNotThrow(battle::startBattle); // if the "player" is not the winner, the battle can be fought again.
        }
    }

    @Test
    void testBattleDevelopment() {
        final var battle = new BasicBattleTile(E1, E2);
        final var report = battle.startBattle();
        assertTrue(E1.isOutOfBanions() || E2.isOutOfBanions());
        assertFalse(E1.isOutOfBanions() && E2.isOutOfBanions());
        for (int i = 0; i < report.size(); i++) {
            final var currentIteratingTurn = report.get(i);
            if (i % 2 == 0) {
                assertEquals(currentIteratingTurn.getEntityOnTurn(), E1);
                assertEquals(currentIteratingTurn.getOtherEntity(), E2);
            } else {
                assertEquals(currentIteratingTurn.getEntityOnTurn(), E2);
                assertEquals(currentIteratingTurn.getOtherEntity(), E1);
            } // controls whether the two entities are alternately on turn
            if (currentIteratingTurn.otherEntityCurrentlyDeployedBanion().isPresent()
                    && currentIteratingTurn instanceof MoveTurn
                    && !((MoveTurn) currentIteratingTurn).getAction().getRight().isAlive() // if the banion dies in the
                                                                                           // current turn, the turn has
                                                                                           // to be a MoveTurn.
                    && i + 1 < report.size()) {
                assertTrue(report.get(i + 1) instanceof SwapTurn);
                assertNotEquals(((SwapTurn) report.get(i + 1)).getAction().getRight().get(),
                        ((MoveTurn) currentIteratingTurn).getAction().getRight()); // controls that the banions are different.
            } // controls if the game entity swaps the banion that died in the previous turn with a new one (different
              // from the previous banion) in the following turn.
        }
    }

    @Test
    void testBattleWinner() {
        final var battle = new BasicBattleTile(E1, E2);
        assertFalse(battle.isWinner(E1));
        final var report = battle.startBattle();
        final var lastTurn = report.get(report.size() - 1);
        final var loser = lastTurn.getOtherEntity(); // the loser is the entity not on turn of the last turn,
        // because the last turn is a MoveTurn and so the entity not on turn is the entity whose banion is killed.
        assertTrue(loser.isOutOfBanions());
        assertFalse(battle.isWinner(loser));
        assertFalse(battle.isWinner(null));
    }

}
