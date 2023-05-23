package darwinsquest.view;

import darwinsquest.view.sound.GameSoundSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the user login.
 */
public class LoginController extends InteractiveController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private Button btEnter;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeBackground();
    }

    private void initializeBackground() {
        final Image image = new Image("img/Blue.png");
        final BackgroundImage bgImg = new BackgroundImage(
                new Image(image.getUrl(), image.getWidth(), image.getHeight(), true, false),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        final Background bg = new Background(bgImg);
        vBox.setBackground(bg);
    }
}

