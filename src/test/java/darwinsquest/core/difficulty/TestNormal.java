package darwinsquest.core.difficulty;

import com.github.javafaker.Faker;
import darwinsquest.ElementFactory;
import darwinsquest.MoveFactory;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.core.gameobject.entity.PlayerImpl;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for {@link Normal}.
 */
class TestNormal {

    @Test
    void opponents() {
        final int hp = 100;

        final Player player = new PlayerImpl(new Faker().name().firstName());
        final var element = new ElementFactory().createElements().stream().findAny().orElseThrow(IllegalStateException::new);

        player.addToInventory(new BanionImpl(element,
            new Faker().name().firstName(),
            hp,
            new MoveFactory().createElements().stream()
                .filter(m -> m.getElement().equals(element))
                .limit(Banion.NUM_MOVES)
                .collect(Collectors.toSet())));

        final var difficulty = new Normal();
        final var board = difficulty.getBoard();

        assertEquals(1, difficulty.getOpponent(player).getInventory().size());
        while (board.move().isPresent()) {
            assertTrue(difficulty.getOpponent(player).getInventory().size() > 0);
        }
    }
}
