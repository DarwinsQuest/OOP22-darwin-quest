package darwinsquest.core;

import darwinsquest.core.element.Air;
import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Grass;
import darwinsquest.core.element.Water;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestTurn {

    private static final int SIZE = 200;
    private static final int HP = 100;
    private static final int DAMAGE_1 = 20;
    private static final int DAMAGE_2 = 15;
    private static final Entity E1 = new OpponentImpl("e1", new BasicAI());
    private static final Entity E2 = new OpponentImpl("e2", new BasicAI());

    @BeforeAll
    static void addBanions() {
        final var b1 = new BanionImpl(new Fire(), "b1", HP);
        final var b2 = new BanionImpl(new Water(), "b2", HP);
        final var b3 = new BanionImpl(new Grass(), "b3", HP);
        final var b4 = new BanionImpl(new Air(), "b4", HP);
        b1.learnMove(new BasicMove(DAMAGE_1, "m1", new Fire()));
        b2.learnMove(new BasicMove(DAMAGE_2, "m2", new Water()));
        b3.learnMove(new BasicMove(DAMAGE_1, "m3", new Grass()));
        b4.learnMove(new BasicMove(DAMAGE_2, "m4", new Air()));
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
        assertThrows(IllegalArgumentException.class, () -> new DeployTurnImpl(turn)); // turn has not already been done.
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
        assertThrows(IllegalArgumentException.class, () -> new MoveTurnImpl(turn)); // turn has not already been done
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
        assertThrows(IllegalArgumentException.class, () -> new SwapTurnImpl(turn)); // the entity on turn does not
                                                                                    // have a currently deployed banion
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
        assertThrows(IllegalStateException.class, turn::onTurnCurrentlyDeployedBanion); // turn not performed yet
        assertThrows(IllegalStateException.class, turn::otherEntityCurrentlyDeployedBanion); // turn not performed yet
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
        assertThrows(IllegalStateException.class, dturn::getAction); // dturn not performed yet
        assertThrows(IllegalStateException.class, mturn::getAction); // mturn not performed yet
        assertThrows(IllegalStateException.class, sturn::getAction); // sturn not performed yet
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
        assertEquals(turn.onTurnCurrentlyDeployedBanion().get(), deployedBanion);
    }

    @Test
    void testMoveTurnDevelopment() {
        final var previousTurn = doDeployTurns();
        final MoveTurn turn = new MoveTurnImpl(previousTurn);
        final var passiveBanionBeforeAction = previousTurn.otherEntityCurrentlyDeployedBanion().get();
        turn.performAction();
        final var actionDone = turn.getAction();
        final var chosenMove = (DamageMove) actionDone.getLeft();
        assertEquals(turn.onTurnCurrentlyDeployedBanion().get(), actionDone.getMiddle());
        assertEquals(turn.otherEntityCurrentlyDeployedBanion().get(), actionDone.getRight());
        assertEquals(chosenMove.getDamage(), passiveBanionBeforeAction.getHp() - actionDone.getRight().getHp());
    }

    @Test
    void testSwapTurnDevelopment() {
        final var previousTurn = doDeployTurns();
        final var swapTurn = new SwapTurnImpl(previousTurn);
        final var oldBanion = previousTurn.otherEntityCurrentlyDeployedBanion().get();
        swapTurn.performAction();
        final var banions = swapTurn.getAction();
        assertEquals(oldBanion, banions.getLeft());
        if (banions.getRight().isPresent()) {
            assertEquals(swapTurn.onTurnCurrentlyDeployedBanion(), banions.getRight());
        }
    }

    private DeployTurn doDeployTurns() {
        final var turn1 = new DeployTurnImpl(E1, E2);
        turn1.performAction();
        final var turn2 = new DeployTurnImpl(turn1);
        turn2.performAction();
        return turn2;
    }

}
