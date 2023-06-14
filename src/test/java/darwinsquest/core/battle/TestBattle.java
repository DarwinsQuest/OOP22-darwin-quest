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
    private static final double DEFAULT_ATK = 1.0;
    private static final double DEFAULT_DEF = 1.0;
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
        final var b1 = new BanionImpl(neutralElement, "b1", HP_1, DEFAULT_ATK, DEFAULT_DEF, b1Moves);
        final var b2 = new BanionImpl(neutralElement, "b2", HP_2, DEFAULT_ATK, DEFAULT_DEF, b2Moves);
        final var b3 = new BanionImpl(neutralElement, "b3", HP_3, DEFAULT_ATK, DEFAULT_DEF, b3Moves);
        final var b4 = new BanionImpl(neutralElement, "b4", HP_4, DEFAULT_ATK, DEFAULT_DEF, b4Moves);
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
        battle.newBattle();
        battle.getPlayer().getInventory().forEach(banion -> assertEquals(banion.getHp(), banion.getMaxHp()));
        boolean nextTurn = battle.nextTurn();
        while (nextTurn) {
            nextTurn = battle.nextTurn();
        }
        if (battle.isWinner(E2)) {
            assertFalse(battle::newBattle);
        } else {
            /*
             * If the player (meaning the GameEntity which starts the battle first) is not the
             * winner, the battle can be fought again.
             */
            assertTrue(battle::newBattle);
            // at the beginning of every battle, the player's banions must have max hp for our logic
            battle.getPlayer().getInventory().forEach(banion -> assertEquals(banion.getHp(), banion.getMaxHp()));
        }
    }

    @Test
    void testBattleDevelopment() {
        final var battle = new BasicBattleTile(E1, E2);
        battle.newBattle();
        boolean nextTurn = battle.nextTurn();
        while (nextTurn) {
            nextTurn = battle.nextTurn();
        }
        final var report = battle.getBattleTurns();
        for (int i = 0; i < report.size(); i++) {
            final var currentIteratingTurn = report.get(i);
            // The following if-statement controls whether the two entities are alternatively on turn
            if (i % 2 == 0) {
                assertEquals(currentIteratingTurn.getEntityOnTurn(), E1);
                assertEquals(currentIteratingTurn.getOtherEntity(), E2);
            } else {
                assertEquals(currentIteratingTurn.getEntityOnTurn(), E2);
                assertEquals(currentIteratingTurn.getOtherEntity(), E1);
            }
            /*
             * The following if-statement controls if a GameEntity swaps the banion that died in the previous turn
             * with a new one (which is different from the previous banion) in the following turn.
             */
            if (currentIteratingTurn.otherEntityCurrentlyDeployedBanion().isPresent()
                    && currentIteratingTurn instanceof MoveTurn
                    /*
                     * If the banion dies in the current turn, the turn has to be a MoveTurn.
                     */
                    && !((MoveTurn) currentIteratingTurn).getAction().getRight().isAlive()
                    && i + 1 < report.size()) {
                assertTrue(report.get(i + 1) instanceof SwapTurn);
                assertNotEquals(((SwapTurn) report.get(i + 1)).getAction().getRight().get(),
                        ((MoveTurn) currentIteratingTurn).getAction().getRight());
            }
        }
    }

    @Test
    void testXpAssignment() {
        final var battle = new BasicBattleTile(E1, E2);
        battle.newBattle();
        boolean nextTurn = battle.nextTurn();
        while (nextTurn) {
            nextTurn = battle.nextTurn();
        }
        battle.getPlayer().getInventory()
                .forEach(banion -> assertTrue(banion.getXp() >= battle.getMinXpBound()
                        && banion.getXp() < banion.getMaxXp() - 1));
    }

    @Test
    void testBattleWinner() {
        final var battle = new BasicBattleTile(E1, E2);
        assertThrows(NullPointerException.class, () -> battle.isWinner(null));
        assertFalse(battle.isWinner(E1)); // the battle has not started yet
        assertFalse(battle.isWinner(E2)); // the battle has not started yet
        battle.newBattle();
        boolean nextTurn = battle.nextTurn();
        while (nextTurn) {
            nextTurn = battle.nextTurn();
        }
        final var report = battle.getBattleTurns();
        final var lastTurn = report.get(report.size() - 1);
        /*
         * The GameEntity that loses the battle is the entity not on turn of the last turn, because the last turn is a
         * MoveTurn and so the GameEntity not on turn is the one whose banion is killed.
         */
        final var loser = lastTurn.getOtherEntity();
        assertFalse(battle.isWinner(loser));
        assertFalse(battle.isWinner(new OpponentImpl("E3", new BasicAI()))); // "E3" has not fought in the battle.
    }

}
