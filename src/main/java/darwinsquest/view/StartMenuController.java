package darwinsquest.view;

import darwinsquest.view.sound.GameSoundSystem;
import darwinsquest.util.JavaFXUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the start menu.
 */
public final class StartMenuController extends StageInteractive implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private VBox vBox;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public StartMenuController(final StageManager manager) {
        super(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
        logo.setImage(new Image("img/logo.png"));
        GameSoundSystem.playMusic("Map.wav", true);
    }

    /**
     * Exit event.
     * @param event the event.
     */
    @FXML
    protected void onExitAction(final ActionEvent event) {
        Platform.runLater(Platform::exit);
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
