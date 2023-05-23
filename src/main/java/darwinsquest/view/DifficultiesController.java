package darwinsquest.view;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import darwinsquest.view.sound.GameSoundSystem;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
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
        // Difficulties should be read from the controller, and relative buttons created
        createButtons(List.of("Normal")); // temporary
        initializeBackground();
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
        GameSoundSystem.playSfx("LowThud.mp3");
    }

    private void initializeBackground() {
        final Image image = new Image("img/Blue.png");
        final BackgroundImage bgImg = new BackgroundImage(
                new Image(image.getUrl(), image.getWidth(), image.getHeight(), true, false),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        final Background bg = new Background(bgImg);
        vbox.setBackground(bg);
    }

}
