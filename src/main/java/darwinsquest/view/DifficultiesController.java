package darwinsquest.view;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

import darwinsquest.Controller;
import darwinsquest.view.sound.GameSoundSystem;
import darwinsquest.util.JavaFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Class that represents the fxml view controller of the difficulty selector.
 */
public class DifficultiesController extends ControllerStageInteractive implements Initializable, EventHandler<ActionEvent> {

    @FXML
    private VBox vBox;

    /**
     * Default constructor.
     * @param manager the stage manager related to this javafx controller.
     * @param controller the MVC controller.
     */
    public DifficultiesController(final StageManager manager, final Controller controller) {
        super(manager, controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
        createButtons(getController().getDifficulties());
    }

    private void createButtons(final Collection<String> difficulties) {
        difficulties.forEach(d -> {
            final var btn = new Button(d);
            btn.setOnAction(this);
            vBox.getChildren().add(btn);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handle(final ActionEvent event) {
        GameSoundSystem.playSfx("LowThud.mp3");
        getController().startGame(((Button) event.getSource()).getText());
        getManager().showBoard();
    }
}
