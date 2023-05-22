package darwinsquest.view;

import darwinsquest.view.sound.GameSoundSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.MediaPlayer;

/**
 * Class that represents the fxml view controller of the user login.
 */
public class LoginController extends InteractiveController {

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public LoginController(final StageManager manager) {
        super(manager);
    }

    /**
     * Enter event.
     * @param event the event.
     */
    @FXML
    protected void onEnterAction(final ActionEvent event) {
        getManager().showDifficulties();
        GameSoundSystem.playSfx("IntroJingle.wav", MediaPlayer::pause, MediaPlayer::play);
    }

    /**
     * Key changed event.
     * @param event the event.
     */
    @FXML
    protected void onUserNameTextChanged(final KeyEvent event) {
        // Probably here should be verified if username is acceptable or not
        // ((TextField) event.getSource()).getText()
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)
                && event.getCode().isLetterKey()
                || event.getCode().isDigitKey()
                || event.getCode().equals(KeyCode.BACK_SPACE)
                || event.getCode().equals(KeyCode.DELETE)) {
            GameSoundSystem.playSfx("LowThud.mp3");
        }

    }
}

