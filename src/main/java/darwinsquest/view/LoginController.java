package darwinsquest.view;

import darwinsquest.Controller;
import darwinsquest.view.sound.GameSoundSystem;
import darwinsquest.view.utility.JavaFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the user login.
 */
public class LoginController extends InteractiveController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private Button btEnter;

    @FXML
    private TextField userName;

    private final Controller controller;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     * @param controller the MVC controller.
     */
    public LoginController(final StageManager manager, final Controller controller) {
        super(manager);
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * Enter event.
     * @param event the event.
     */
    @FXML
    protected void onEnterAction(final ActionEvent event) {
        controller.login(userName.getText());
        getManager().setUsername(userName.getText());
        getManager().showDifficulties();
        GameSoundSystem.playSfx("IntroJingle.wav", MediaPlayer::pause, MediaPlayer::play);
    }

    /**
     * Key changed event.
     * @param event the event.
     */
    @FXML
    protected void onUserNameTextChanged(final KeyEvent event) {
        btEnter.setDisable(!controller.isValidUsername(((TextField) event.getSource()).getText()));
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)
                && event.getCode().isLetterKey()
                || event.getCode().isDigitKey()
                || event.getCode().equals(KeyCode.BACK_SPACE)
                || event.getCode().equals(KeyCode.DELETE)) {
            GameSoundSystem.playSfx("LowThud.mp3");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
    }
}

