package darwinsquest.view;

import darwinsquest.MainController;
import darwinsquest.annotation.Description;
import darwinsquest.view.sound.GameSoundSystem;
import darwinsquest.util.JavaFXUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the start menu.
 */
@Description("startmenu")
public final class StartMenuView extends ControllerInteractive<MainController> implements Initializable {

    @FXML
    private ImageView logo;
    @FXML
    private VBox vBox;

    /**
     * Default constructor.
     * @param controller the MVC controller.
     */
    public StartMenuView(final MainController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
        logo.setImage(new Image("img/logo.png"));
        GameSoundSystem.playMusic("Map.wav", true);
        JavaFXUtils.bindButtonsWidthToMax(vBox);
    }

    @FXML
    private void onExitAction() { // NOPMD, events are indirectly used
        Platform.runLater(Platform::exit);
    }

    @FXML
    private void onPlayAction() { // NOPMD, events are indirectly used
        getController().startController();
        GameSoundSystem.playSfx("LowThud.mp3");
    }

    @FXML
    private void onEscPressed(final KeyEvent event) { // NOPMD, events are indirectly used
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            getController().showSettings();
        }
    }

}
