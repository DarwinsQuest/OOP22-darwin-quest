package darwinsquest;

import darwinsquest.core.EngineImpl;
import darwinsquest.core.gameobject.entity.PlayerImpl;
import darwinsquest.view.JavaFXApplication;
import javafx.application.Application;

import darwinsquest.core.Engine;

import java.util.Objects;
import java.util.Set;

/**
 * Class that represents this project controller.
 * This is the startup point of the application.
 */
public final class ControllerImpl implements Controller {

    private Engine engine;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidUsername(final String username) {
        return PlayerImpl.isNameValid(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(final String username) {
        engine = new EngineImpl(new PlayerImpl(username));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getDifficulties() {
        return Objects.requireNonNull(engine).getDifficulties();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean startGame(final String difficulty) {
        return Objects.requireNonNull(engine).startGame(difficulty);
    }

    /**
     * Application entry-point.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }
}
