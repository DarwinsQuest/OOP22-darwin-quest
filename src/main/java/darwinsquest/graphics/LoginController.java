package darwinsquest.graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

/**
 * Class that represents the fxml view controller of the user login.
 */
public class LoginController extends InteractiveController {

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
        getManager().showBattle();
    }

    /**
     * Key changed event.
     * @param event the event.
     */
    @FXML
    protected void onUserNameTextChanged(final KeyEvent event) {
        // Probably here should be verified if username is acceptable or not
        // ((TextField) event.getSource()).getText()
    }
}

