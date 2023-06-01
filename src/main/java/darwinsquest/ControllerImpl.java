package darwinsquest;

import darwinsquest.core.EngineImpl;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.view.JavaFXApplication;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;

import darwinsquest.core.Engine;

import java.util.Objects;
import java.util.Set;

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
        this.engine = new EngineImpl(player);
        view.setStageTitlePrefix(player.getName());
        view.show(view.createDifficultySelectorView());
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
    public void startGame(final String difficulty) {
        Objects.requireNonNull(engine).startGame(difficulty);
        final var boardController = new BoardControllerImpl(Objects.requireNonNull(engine).getBoard().orElseThrow());
        final var boardView = view.createBoardView(boardController);
        boardController.init(boardView);
        view.show(boardView);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public boolean startBattle() {
//        return Objects.requireNonNull(engine).getBoard().orElseThrow().startBattle();
//    }

    /**
     * Application entry-point.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXApplication.class, args);
    }
}
