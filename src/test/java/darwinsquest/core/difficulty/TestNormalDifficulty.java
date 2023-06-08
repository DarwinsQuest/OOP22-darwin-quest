package darwinsquest.core.difficulty;

import com.github.javafaker.Faker;
import darwinsquest.generation.ElementFactory;
import darwinsquest.generation.MoveFactory;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link NormalDifficulty}.
 */
class TestNormalDifficulty {

    @Test
    void opponents() {
        final int hp = 100;
        final var atk = 1.0;
        final var def = 1.0;

        final var player = new OpponentImpl(new Faker().name().firstName(), new BasicAI());
        final var element = new ElementFactory().createElements().stream().findAny().orElseThrow();

        player.addToInventory(new BanionImpl(element,
            new Faker().name().firstName(),
            hp,
            atk,
            def,
            new MoveFactory().createElements().stream()
                .filter(m -> m.getElement().equals(element))
                .limit(Banion.NUM_MOVES)
                .collect(Collectors.toSet())));

        final var difficulty = new NormalDifficulty(player);
        final var board = difficulty.getBoard();

        final var oppFactory = new OpponentsFactoryImpl(
            NormalDifficulty.MIN_OPP_BANIONS,
            NormalDifficulty.MAX_OPP_BANIONS,
            BasicAI.class);
        assertEquals(1, oppFactory.createOpponent(board, player).getInventory().size());
        board.startBattle();
        while (board.move().isPresent()) {
            assertTrue(oppFactory.createOpponent(board, player).getInventory().size() > 0);
            board.startBattle();
        }
    }
}
