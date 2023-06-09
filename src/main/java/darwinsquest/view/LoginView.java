package darwinsquest.view;

import darwinsquest.controller.LoginController;
import darwinsquest.annotation.Description;
import darwinsquest.view.sound.GameSoundSystem;
import darwinsquest.util.JavaFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the user login.
 */
@Description("login")
public class LoginView extends ControllerInteractive<LoginController> implements Initializable {

    @FXML
    private VBox vBox;
    @FXML
    private Button btEnter;
    @FXML
    private TextField userName;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the MVC controller.
     */
    public LoginView(final View view, final LoginController controller) {
        super(view, controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
    }

    @FXML
    private void onEnterAction() { // NOPMD, events are indirectly used
        GameSoundSystem.playSfx("IntroJingle.wav", MediaPlayer::pause, MediaPlayer::play);
        getController().login(userName.getText());
    }

    @FXML
    private void onUserNameTextChanged(final KeyEvent event) { // NOPMD, events are indirectly used
        btEnter.setDisable(!getController().isValidUsername(((TextField) event.getSource()).getText()));
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)
                && event.getCode().isLetterKey()
                || event.getCode().isDigitKey()
                || event.getCode().equals(KeyCode.BACK_SPACE)
                || event.getCode().equals(KeyCode.DELETE)) {
            GameSoundSystem.playSfx("LowThud.mp3");
        }
    }
}

