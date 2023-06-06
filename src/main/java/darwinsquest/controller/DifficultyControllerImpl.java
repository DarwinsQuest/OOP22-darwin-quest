package darwinsquest.controller;

import darwinsquest.core.Engine;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a difficulty controller.
 */
public final class DifficultyControllerImpl implements DifficultyController {

    private final ControllerManager controller;
    private final Engine engine;

    /**
     * Default constructor.
     * @param controller the main controller.
     * @param engine the game engine.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Main controller is needed to separate concerns.")
    public DifficultyControllerImpl(final ControllerManager controller, final Engine engine) {
        this.controller = Objects.requireNonNull(controller);
        this.engine = Objects.requireNonNull(engine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getDifficulties() {
        return engine.getDifficulties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean selectDifficulty(final String difficulty) {
        if (engine.startGame(difficulty)) {
            controller.startBoard();
            return true;
        }
        return false;
    }
}
