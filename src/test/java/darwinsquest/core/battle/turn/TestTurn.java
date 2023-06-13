package darwinsquest.core.battle.turn;

import darwinsquest.core.difficulty.BasicAI;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.element.ImmutableElement;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import darwinsquest.core.gameobject.move.BasicMove;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.move.DamageMove;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestTurn {

    private static final int SIZE = 200;
    private static final int HP = 100;
    private static final double ATK = 1.0;
    private static final double DEF = 1.0;
    private static final int DAMAGE_1 = 20;
    private static final int DAMAGE_2 = 15;
    private static final GameEntity E1 = new OpponentImpl("e1", new BasicAI());
    private static final GameEntity E2 = new OpponentImpl("e2", new BasicAI());

    @BeforeAll
    static void addBanions() {
        final var fire = new ImmutableElement("Fire", Set.of(), Set.of());
        final var water = new ImmutableElement("Water", Set.of(), Set.of());
        final var grass = new ImmutableElement("Grass", Set.of(), Set.of());
        final var air = new ImmutableElement("Air", Set.of(), Set.of());
        final Set<Move> b1Moves = Set.of(new BasicMove(DAMAGE_1, "m1", fire),
                new BasicMove(DAMAGE_2, "m2", fire),
                new BasicMove(DAMAGE_1, "m3", fire),
                new BasicMove(DAMAGE_2, "m4", fire));
        final Set<Move> b2Moves = Set.of(new BasicMove(DAMAGE_1, "m1", water),
                new BasicMove(DAMAGE_2, "m2", water),
                new BasicMove(DAMAGE_1, "m3", water),
                new BasicMove(DAMAGE_2, "m4", water));
        final Set<Move> b3Moves = Set.of(new BasicMove(DAMAGE_1, "m1", grass),
                new BasicMove(DAMAGE_2, "m2", grass),
                new BasicMove(DAMAGE_1, "m3", grass),
                new BasicMove(DAMAGE_2, "m4", grass));
        final Set<Move> b4Moves = Set.of(new BasicMove(DAMAGE_1, "m1", air),
                new BasicMove(DAMAGE_2, "m2", air),
                new BasicMove(DAMAGE_1, "m3", air),
                new BasicMove(DAMAGE_2, "m4", air));
        final var b1 = new BanionImpl(fire, "b1", HP, ATK, DEF, b1Moves);
        final var b2 = new BanionImpl(water, "b2", HP, ATK, DEF, b2Moves);
        final var b3 = new BanionImpl(grass, "b3", HP, ATK, DEF, b3Moves);
        final var b4 = new BanionImpl(air, "b4", HP, ATK, DEF, b4Moves);
        E1.addToInventory(b1);
        E1.addToInventory(b2);
        E2.addToInventory(b3);
        E2.addToInventory(b4);
    }

    @Test
    void testDeployTurnCreation() {
        final Turn turn = new DeployTurnImpl(E1, E2);
        assertThrows(IllegalArgumentException.class, () -> new DeployTurnImpl(null, E1));
        assertThrows(IllegalArgumentException.class, () -> new DeployTurnImpl(E1, null));
        assertThrows(IllegalArgumentException.class, () -> new DeployTurnImpl(null));
        assertThrows(IllegalArgumentException.class, () -> new DeployTurnImpl(turn)); // turn has not been done yet.
        turn.performAction();
        final var deployTurn = new DeployTurnImpl(turn);
        deployTurn.performAction();
        assertEquals(deployTurn.getEntityOnTurn(), turn.getOtherEntity());
        assertEquals(deployTurn.getOtherEntity(), turn.getEntityOnTurn());
        assertEquals(deployTurn.otherEntityCurrentlyDeployedBanion(), turn.onTurnCurrentlyDeployedBanion());
    }

    @Test
    void testMoveTurnCreation() {
        final Turn turn = new DeployTurnImpl(E1, E2);
        assertThrows(IllegalArgumentException.class, () -> new MoveTurnImpl(null));
        assertThrows(IllegalArgumentException.class, () -> new MoveTurnImpl(turn)); // turn has not yet been done
        turn.performAction();
        assertThrows(IllegalArgumentException.class, () -> new MoveTurnImpl(turn)); // not all the entities have a current banion
        final var deployTurn = doDeployTurns();
        final var moveTurn = new MoveTurnImpl(deployTurn);
        moveTurn.performAction();
        assertEquals(moveTurn.getEntityOnTurn(), deployTurn.getOtherEntity());
        assertEquals(moveTurn.getOtherEntity(), deployTurn.getEntityOnTurn());
        assertEquals(moveTurn.onTurnCurrentlyDeployedBanion(), deployTurn.otherEntityCurrentlyDeployedBanion());
        assertEquals(moveTurn.otherEntityCurrentlyDeployedBanion(), deployTurn.onTurnCurrentlyDeployedBanion());
    }

    @Test
    void testSwapTurnCreation() {
        final Turn turn = new DeployTurnImpl(E1, E2);
        assertThrows(IllegalArgumentException.class, () -> new SwapTurnImpl(null));
        assertThrows(IllegalArgumentException.class, () -> new SwapTurnImpl(turn)); // turn has not already been done
        turn.performAction();
        /*
         * The entity on turn does not have a currently deployed banion.
         */
        assertThrows(IllegalArgumentException.class, () -> new SwapTurnImpl(turn));
        final var deployTurn = doDeployTurns();
        final var swapTurn = new SwapTurnImpl(deployTurn);
        swapTurn.performAction();
        assertEquals(swapTurn.getEntityOnTurn(), deployTurn.getOtherEntity());
        assertEquals(swapTurn.getOtherEntity(), deployTurn.getEntityOnTurn());
        assertEquals(swapTurn.otherEntityCurrentlyDeployedBanion(), deployTurn.onTurnCurrentlyDeployedBanion());
    }

    @Test
    void testEquality() {
        final var turns = Stream.generate(() -> new DeployTurnImpl(E1, E2)).limit(SIZE).toList();
        turns.forEach(t1 -> turns.stream().filter(t2 -> t2 != t1).forEach(t3 -> assertNotEquals(t1, t3)));
    }

    @Test
    void testBanionGetters() {
        final Turn turn = new DeployTurnImpl(E1, E2);
        assertThrows(IllegalStateException.class, turn::onTurnCurrentlyDeployedBanion); // turn has not been performed yet
        assertThrows(IllegalStateException.class, turn::otherEntityCurrentlyDeployedBanion); // turn has not been performed yet
        turn.performAction();
        assertDoesNotThrow(turn::onTurnCurrentlyDeployedBanion);
        assertDoesNotThrow(turn::otherEntityCurrentlyDeployedBanion);
    }

    @Test
    void testEntityGetters() {
        final Turn turn = new DeployTurnImpl(E1, E2);
        assertEquals(E1, turn.getEntityOnTurn());
        assertEquals(E2, turn.getOtherEntity());
    }

    @Test
    void testGetAction() {
        final DeployTurn dturn = new DeployTurnImpl(E1, E2);
        final MoveTurn mturn = new MoveTurnImpl(doDeployTurns());
        final SwapTurn sturn = new SwapTurnImpl(doDeployTurns());
        assertThrows(IllegalStateException.class, dturn::getAction); // dturn has not been performed yet
        assertThrows(IllegalStateException.class, mturn::getAction); // mturn has not been performed yet
        assertThrows(IllegalStateException.class, sturn::getAction); // sturn has not been performed yet
        dturn.performAction();
        mturn.performAction();
        sturn.performAction();
        assertDoesNotThrow(dturn::getAction);
        assertDoesNotThrow(mturn::getAction);
        assertDoesNotThrow(sturn::getAction);
    }

    @Test
    void testDeployTurnDevelopment() {
        final DeployTurn turn = new DeployTurnImpl(E1, E2);
        turn.performAction();
        final var deployedBanion = turn.getAction();
        assertBanionEquality(turn.onTurnCurrentlyDeployedBanion().get(), deployedBanion);
    }

    @Test
    void testMoveTurnDevelopment() {
        final var previousTurn = doDeployTurns();
        final MoveTurn turn = new MoveTurnImpl(previousTurn);
        final var passiveBanionBeforeAction = previousTurn.otherEntityCurrentlyDeployedBanion().get();
        turn.performAction();
        final var actionDone = turn.getAction();
        final var chosenMove = (DamageMove) actionDone.getLeft();
        assertBanionEquality(turn.onTurnCurrentlyDeployedBanion().get(), actionDone.getMiddle());
        assertBanionEquality(turn.otherEntityCurrentlyDeployedBanion().get(), actionDone.getRight());
        assertEquals(chosenMove.computeDamage(actionDone.getMiddle(), actionDone.getRight()),
                passiveBanionBeforeAction.getHp() - actionDone.getRight().getHp());
    }

    @Test
    void testSwapTurnDevelopment() {
        final var previousTurn = doDeployTurns();
        final var swapTurn = new SwapTurnImpl(previousTurn);
        final var oldBanion = previousTurn.otherEntityCurrentlyDeployedBanion().get();
        swapTurn.performAction();
        final var banions = swapTurn.getAction();
        assertBanionEquality(oldBanion, banions.getLeft());
        if (banions.getRight().isPresent()) {
            assertBanionEquality(swapTurn.onTurnCurrentlyDeployedBanion().get(), banions.getRight().get());
        }
    }

    private DeployTurn doDeployTurns() {
        final var turn1 = new DeployTurnImpl(E1, E2);
        turn1.performAction();
        final var turn2 = new DeployTurnImpl(turn1);
        turn2.performAction();
        return turn2;
    }

    private void assertBanionEquality(final Banion b1, final Banion b2) {
        assertEquals(b1.getHp(), b2.getHp());
        assertEquals(b1.getElement(), b2.getElement());
        assertEquals(b1.getName(), b2.getName());
        assertEquals(b1.getMaxHp(), b2.getMaxHp());
    }

}
