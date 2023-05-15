package darwinsquest.graphics;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Class that represents the fxml view controller of the start menu.
 */
public final class StartMenuController implements Initializable {

    @FXML
    private Button btExit;

    @FXML
    private Button btPlay;

    @FXML
    private ImageView titleImage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        // titleImage.setImage(new Image(ClassLoader.getSystemResourceAsStream("img/green.png")));
    }

    /**
     * Exit event.
     * @param event the event.
     */
    @FXML
    void onExitAction(final ActionEvent event) {
        Platform.runLater(() -> Platform.exit());
    }

    /**
     * Play event.
     * @param event the event.
     */
    @FXML
    void onPlayAction(final ActionEvent event) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
