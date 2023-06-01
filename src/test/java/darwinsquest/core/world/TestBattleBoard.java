package darwinsquest.core.world;

import com.github.javafaker.Faker;
import darwinsquest.generation.BanionFactory;
import darwinsquest.core.difficulty.BasicAI;
import darwinsquest.core.difficulty.Die;
import darwinsquest.core.difficulty.Normal;
import darwinsquest.core.difficulty.OpponentsFactoryImpl;
import darwinsquest.core.gameobject.entity.OpponentImpl;
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
        final var player = new OpponentImpl(new Faker().name().firstName(), new BasicAI());
        player.addToInventory(
            new BanionFactory().createElements().stream()
                .limit(numBanion)
                .collect(Collectors.toSet()));
        final var battleBoard = new BattleBoardImpl(levels, new Die(),
            new OpponentsFactoryImpl(Normal.MIN_OPP_BANIONS, Normal.MAX_OPP_BANIONS, BasicAI.class),
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
