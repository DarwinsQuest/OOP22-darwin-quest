package darwinsquest.controller;

import darwinsquest.core.Engine;
import darwinsquest.core.EngineImpl;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.view.JavaFXView;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.application.Application;

import java.util.Objects;

/**
 * Class that represents this project controller.
 * This is the startup point of the application.
 */
public final class ControllerImpl implements ControllerManager {

    private final View view;
    private Engine engine;
    private Player player;

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
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Player is needed.")
    @Override
    public void setPlayer(final Player player) {
        this.player = Objects.requireNonNull(player);
        engine = new EngineImpl(player);
        view.setWindowTitlePrefix(player.getName());
        selectFirstPlayerBanions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectFirstPlayerBanions() {
        final var numBanions = 4;
        view.show(view.createBanionSelectorView(
            new SelectBanionControllerImpl(this, new EntityControllerImpl(player), numBanions)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectDifficulty() {
        view.show(view.createDifficultySelectorView(new DifficultyControllerImpl(this, engine)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startBoard() {
        final var boardController = new BoardControllerImpl(engine.getBoard().orElseThrow(), view);
        final var boardView = view.createBoardView(boardController);
        boardController.setBoardView(boardView);
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
