package darwinsquest.core.difficulty;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import darwinsquest.BanionFactory;
import darwinsquest.core.Banion;
import darwinsquest.core.BanionImpl;
import darwinsquest.core.BasicMove;
import darwinsquest.core.Move;
import darwinsquest.core.decision.Decision;
import darwinsquest.core.element.Element;
import darwinsquest.core.element.Neutral;

import org.junit.jupiter.api.Test;

class TestBasicAI {

    private static final int SEED = 353_232;
    private static final int N_ITERATIONS = 100;
    private static final int BANION_HP = 100;
    private static final int MOVE_DAMAGE = 10;

    private final AI ai = new Normal().getAI();

    private final Element neutral = new Neutral();

    private final Collection<Banion> banions = new BanionFactory().createElements().get();
    private final Collection<Move> moves = Set.of(new BasicMove(MOVE_DAMAGE, "1", neutral),
        new BasicMove(MOVE_DAMAGE, "2", neutral),
        new BasicMove(MOVE_DAMAGE, "3", neutral),
        new BasicMove(MOVE_DAMAGE, "4", neutral));

    @Test
    void testDeployBanion() {
        final Banion deployedBanion = ai.decideBanionDeployment(banions);
        assertTrue(banions.contains(deployedBanion));
    }

    @Test
    void testMoveSelection() {
        final Move chosenMove = ai.decideMoveSelection(moves);
        assertTrue(moves.contains(chosenMove));
    }

    @Test
    void testBanionSwap() {
        final Collection<Banion> banions = new HashSet<>();
        final Banion b1 = new BanionImpl(neutral, "Grassy", BANION_HP, moves);
        final Banion b2 = new BanionImpl(neutral, "Airy", BANION_HP, moves);
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
