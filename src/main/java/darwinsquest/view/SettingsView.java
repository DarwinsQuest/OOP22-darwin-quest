package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.sound.GameSoundSystem;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the settings scene.
 */
@Description("settings")
public class SettingsView implements Initializable {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private final View view;
    private final Object previousScene;

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
     * @param view the MVC view.
     * @param previousScene the previous scene.
     */
    protected SettingsView(final View view, final Object previousScene) {
        this.view = Objects.requireNonNull(view);
        this.previousScene = Objects.requireNonNull(previousScene);
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
        volumeSlider.setValue(GameSoundSystem.getMasterVolume());
    }

    /**
     * {@inheritDoc}
     */
    @FXML
    void onCloseButtonPressed(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        view.show(previousScene);
    }

    @FXML
    private void onEscPressed(final KeyEvent event) { // NOPMD, events are indirectly used
        view.show(previousScene);
    }
}

