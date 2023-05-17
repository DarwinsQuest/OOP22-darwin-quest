package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import darwinsquest.core.decision.Decision;
import org.junit.jupiter.api.Test;
import darwinsquest.core.element.Air;
import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Grass;
import darwinsquest.core.element.Water;

class TestBasicAI {

    private static final int SEED = 353_232;
    private static final int N_ITERATIONS = 100;
    private static final int BANION_HP = 100;
    private static final int MOVE_DAMAGE = 10;

    @Test
    void testDeployBanion() {
        final AI ai = new BasicAI();
        final Collection<Banion> banions = new HashSet<>();
        final Banion b1 = new BanionImpl(new Fire(), "Firey", BANION_HP);
        final Banion b2 = new BanionImpl(new Water(), "Watery", BANION_HP);
        banions.add(b1);
        banions.add(b2);
        final Banion deployedBanion = ai.decideBanionDeployment(banions);
        assertTrue(banions.contains(deployedBanion));
    }

    @Test
    void testMoveSelection() {
        final AI ai = new BasicAI();
        final Collection<Move> moves = new HashSet<>();
        final Move m1 = new BasicMove(MOVE_DAMAGE, "Fireball", new Fire());
        final Move m2 = new BasicMove(MOVE_DAMAGE, "Explosion", new Fire());
        moves.add(m1);
        moves.add(m2);
        final Move chosenMove = ai.decideMoveSelection(moves);
        assertTrue(moves.contains(chosenMove));
    }

    @Test
    void testBanionSwap() {
        final AI ai = new BasicAI();
        final Collection<Banion> banions = new HashSet<>();
        final Banion b1 = new BanionImpl(new Grass(), "Grassy", BANION_HP);
        final Banion b2 = new BanionImpl(new Air(), "Airy", BANION_HP);
        banions.add(b1);
        banions.add(b2);
        final Optional<Banion> deployedBanion1 = ai.decideBanionSwap(banions);
        assertTrue(deployedBanion1.isPresent() && banions.contains(deployedBanion1.get()));
        b2.setHp(0);
        final Optional<Banion> deployedBanion2 = ai.decideBanionSwap(banions);
        assertTrue(deployedBanion2.isPresent() && deployedBanion2.get().equals(b1));
        b1.setHp(0);
        final Optional<Banion> deployedBanion3 = ai.decideBanionSwap(banions);
        assertTrue(deployedBanion3.isEmpty());
    }

    @Test
    void testGetDecision() {
        final BasicAI ai1 = new BasicAI(SEED);
        final BasicAI ai2 = new BasicAI(SEED);
        final Collection<Decision> decisions1 = new ArrayList<>();
        final Collection<Decision> decisions2 = new ArrayList<>();
        for (int x = 1; x <= N_ITERATIONS; x++) {
            decisions1.add(ai1.getDecision());
            decisions2.add(ai2.getDecision());
        }
        assertEquals(decisions1, decisions2);
    }

}
