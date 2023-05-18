package darwinsquest.graphics;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Class that represents the fxml view controller of the difficulty selector.
 */
public class DifficultiesController extends InteractiveController implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private VBox vbox;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public DifficultiesController(final StageManager manager) {
        super(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // Difficulties should be read from controller, and relative buttons created
        createButtons(List.of("Normal")); // temporary
    }

    private void createButtons(final Collection<String> difficulties) {
        difficulties.stream().forEach(d -> {
            final var btn = new Button(d);
            btn.setOnAction(this);
            vbox.getChildren().add(btn);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final ActionEvent event) {
        // Chose difficulty sent to controller
        getManager().showBattle();
    }
}
