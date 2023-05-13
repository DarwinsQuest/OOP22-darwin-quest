package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Test Class for {@link darwinsquest.core.EngineImpl}.
 */
class TestEngine {

    @Test
    void difficulties() {
        final var engine = new EngineImpl();

        engine.getDifficulties().forEach(engine::startGame);
        assertEquals(Optional.empty(), engine.startGame(""));
    }
}
