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

    private static final String PATH_CONFIG = "config/";

    /**
     * Path to {@link darwinsquest.core.gameobject.element.Element} definitions.
     */
    public static final String PATH_ELEMENTS = PATH_CONFIG + "elements.json";
    /**
     * Path to {@link darwinsquest.core.gameobject.Move} definitions.
     */
    public static final String PATH_MOVES = PATH_CONFIG + "moves.json";
    /**
     * Path to {@link darwinsquest.core.gameobject.banion.Banion} definitions.
     */
    public static final String PATH_BANIONS = PATH_CONFIG + "banions.json";

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
