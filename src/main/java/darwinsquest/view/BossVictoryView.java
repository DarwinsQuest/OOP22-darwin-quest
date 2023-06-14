package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.sound.GameSoundSystem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View controller for the final victory menu.
 */
@Description("finalmenu")
public final class BossVictoryView implements Initializable {
    private static final String VICTORY_SOUND = "WinJingle.wav";
    @FXML
    private BorderPane pane;
    @FXML
    private Label label;
    @FXML
    private Button quitButton;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        label.setText("You defeated the final Boss!");
        JavaFXUtils.initializeBackground(pane, "img/Green.png");
        GameSoundSystem.stopAll();
        GameSoundSystem.playSfx(VICTORY_SOUND);
    }

    @FXML
    void onQuitButton(final ActionEvent event) {
        Platform.runLater(Platform::exit);
    }

}
