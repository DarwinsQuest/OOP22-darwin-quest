package darwinsquest;

import darwinsquest.core.EngineImpl;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.view.JavaFXView;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;

import darwinsquest.core.Engine;

import java.util.Objects;

/**
 * Class that represents this project controller.
 * This is the startup point of the application.
 */
public final class ControllerImpl implements ControllerManager {

    private final View view;
    private Engine engine;

    /**
     * Default constructor.
     * @param view the MVC view.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed according to MVC.")
    public ControllerImpl(final View view) {
        this.view = Objects.requireNonNull(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startController() {
        view.show(view.createLoginView(new LoginControllerImpl(this)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(final Player player) {
        engine = new EngineImpl(player);
        view.setWindowTitlePrefix(player.getName());
        view.show(view.createDifficultySelectorView(new DifficultyControllerImpl(this, engine)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBoard() {
        final var boardController = new BoardControllerImpl(engine.getBoard().orElseThrow());
        final var boardView = view.createBoardView(boardController);
        boardController.init(boardView);
        view.show(boardView);
    }

    /**
     * Application entry-point.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXView.class, args);
    }
}
