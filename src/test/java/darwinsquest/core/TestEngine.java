package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.random.RandomGenerator;

import darwinsquest.core.gameobject.entity.PlayerImpl;
import org.junit.jupiter.api.Test;

/**
 * Test Class for {@link darwinsquest.core.EngineImpl}.
 */
class TestEngine {

    private static final String PLAYER_NAME = "I";
    private final RandomGenerator generator = new Random();

    @Test
    void difficulties() {
        final var engine = new EngineImpl(new PlayerImpl(PLAYER_NAME));

        engine.getDifficulties().forEach(difficulty ->
            assertTrue(engine.startGame(difficulty)));

        var stringLength = 0;
        var generatedDifficulty = "";

        while (engine.getDifficulties().contains(generatedDifficulty)) {
            final var array = new byte[++stringLength];
            generator.nextBytes(array);
            generatedDifficulty = new String(array, StandardCharsets.UTF_8);
        }

        final var nonexistingDifficulty = generatedDifficulty;
        assertFalse(engine.startGame(nonexistingDifficulty));
    }
}
