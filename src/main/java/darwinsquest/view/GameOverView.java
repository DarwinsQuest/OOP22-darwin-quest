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
 * View controller for the game over menu.
 */
@Description("finalmenu")
public final class GameOverView implements Initializable {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private static final String GAME_OVER_SOUND = "WarpJingle.wav";

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
        label.setText("GAMEOVER");
        JavaFXUtils.initializeBackground(pane, "img/Purple.png");
        GameSoundSystem.stopAll();
        GameSoundSystem.playSfx(GAME_OVER_SOUND);
    }

    @FXML
    void onQuitButton(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        Platform.runLater(Platform::exit);
    }

}
