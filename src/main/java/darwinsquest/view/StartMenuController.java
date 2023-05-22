package darwinsquest.view;

import darwinsquest.view.sound.GameSoundSystem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Class that represents the fxml view controller of the start menu.
 */
public final class StartMenuController extends InteractiveController {

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public StartMenuController(final StageManager manager) {
        super(manager);
        GameSoundSystem.playMusic("Map.wav", true);
    }

    /**
     * Exit event.
     * @param event the event.
     */
    @FXML
    protected void onExitAction(final ActionEvent event) {
        Platform.runLater(() -> Platform.exit());
    }

    /**
     * Play event.
     * @param event the event.
     */
    @FXML
    protected void onPlayAction(final ActionEvent event) {
        getManager().showLogin();
        GameSoundSystem.playSfx("LowThud.mp3");
    }
}
