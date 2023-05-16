package darwinsquest.graphics;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Class that represents the fxml view controller of the start menu.
 */
public final class StartMenuController extends InteractiveController {

    @FXML
    private Button btExit;

    @FXML
    private Button btPlay;

    @FXML
    private ImageView titleImage;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public StartMenuController(final StageManager manager) {
        super(manager);
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
        getManager().showBattle();
    }
}
