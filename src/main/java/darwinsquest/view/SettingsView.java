package darwinsquest.view;

import darwinsquest.MainController;
import darwinsquest.annotation.Description;
import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.sound.GameSoundSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the settings scene.
 */
@Description("settings")
public class SettingsView extends ControllerInteractive<MainController> implements Initializable, View {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
//    private final Scene previousScene;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button closeButton;
    @FXML
    private Label settingsLabel;
    @FXML
    private Label volumeLabel;
    @FXML
    private Slider volumeSlider;

    /**
     * Default constructor.
     * @param controller the controller.
     */
    protected SettingsView(final MainController controller/*, final Scene previousScene*/) {
        super(controller);
//        this.previousScene = previousScene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(borderPane, "img/Blue.png");
        // Changes the game volume dynamically on slider movement.
        volumeSlider.valueProperty()
                .addListener((observable, oldValue, newValue) -> GameSoundSystem.setMasterVolume(volumeSlider.getValue()));
    }

    /**
     * {@inheritDoc}
     */
    @FXML
    void onCloseButtonPressed(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
//        final var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        stage.setScene(previousScene);
//        stage.show();
    }

}

