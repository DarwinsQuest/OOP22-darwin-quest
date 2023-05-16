package darwinsquest.graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the battle scene.
 */
public final class BattleController extends InteractiveController implements Initializable {

    private static final double WIDTH = 0.3;
    private static final double HEIGHT = 0.3;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button forfeitBtn;

    @FXML
    private Button inventoryBtn;

    @FXML
    private HBox lowerButtonsBox;

    @FXML
    private Button moveBtn1;

    @FXML
    private Button moveBtn2;

    @FXML
    private Button moveBtn3;

    @FXML
    private Button moveBtn4;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    public BattleController(final StageManager manager) {
        super(manager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeBackground();
        initializeButtons();
    }

    private void initializeBackground() {
        final Image img = new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/bg.png")));
        final BackgroundSize bgSize = new BackgroundSize(
                WIDTH,
                HEIGHT,
                true,
                true,
                true,
                false);
        final BackgroundImage bgImg = new BackgroundImage(img,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                bgSize
        );
        final Background bg = new Background(bgImg);
        borderPane.setBackground(bg);
    }

    // This method allows only one focused/hovered button at once.
    private void initializeButtons() {
        final List<Button> buttonList = new ArrayList<>(List.of(
                moveBtn1,
                moveBtn2,
                moveBtn3,
                moveBtn4,
                inventoryBtn,
                forfeitBtn));
        buttonList.forEach(b -> b.setOnMouseEntered(event -> b.requestFocus()));
        buttonList.forEach(b -> b.setOnMouseExited(event -> b.getParent().requestFocus()));
    }

}
