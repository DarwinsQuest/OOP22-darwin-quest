package darwinsquest.core.world;

import com.github.javafaker.Faker;
import darwinsquest.controller.PlayerInput;
import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.PlayerImpl;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.generation.BanionFactory;
import darwinsquest.core.difficulty.BasicAI;
import darwinsquest.core.difficulty.Die;
import darwinsquest.core.difficulty.NormalDifficulty;
import darwinsquest.core.difficulty.OpponentsFactoryImpl;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test Class for {@link BattleBoard}.
 */
class TestBattleBoard {

    @Test
    void creation() {
        assertThrows(IllegalArgumentException.class,
            () -> new BattleBoardImpl(0, null, null, null));
    }

    @Test
    void management() {
        final var loopOp = 100;
        final var levels = 10;
        final var numBanion = 4;
        final var player = new PlayerImpl(new Faker().name().firstName());
        player.setInput(new PlayerInput() {

            private final BasicAI ai = new BasicAI();

            /**
             * {@inheritDoc}
             */
            @Override
            public Banion deployBanion() {
                return ai.decideBanionDeployment(player.getInventory());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Move selectMove() {
                // very bad way to do this, but it is the only option for how classes are structured
                return ai.decideMoveSelection(player.getInventory().stream().findAny().orElseThrow().getMoves());
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Banion swapBanion() {
                return ai.decideBanionSwap(player.getInventory()).orElseThrow();
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Decision getDecision() {
                return ai.getDecision();
            }
        });
        player.addToInventory(
            new BanionFactory().createElements().stream()
                .limit(numBanion)
                .collect(Collectors.toSet()));
        final var battleBoard = new BattleBoardImpl(
            levels,
            new Die(),
            new OpponentsFactoryImpl(NormalDifficulty.MIN_OPP_BANIONS, NormalDifficulty.MAX_OPP_BANIONS, BasicAI.class),
            player);

        battleBoard.startBattle();
        while (battleBoard.move().isPresent()) {
            assertFalse(battleBoard.canMove());
            assertFalse(battleBoard.isBattleWon());
            IntStream.range(0, loopOp).forEach(i -> assertFalse(battleBoard.move().isPresent()));
            battleBoard.startBattle();
        }
        assertFalse(battleBoard.canMove());
    }
}
