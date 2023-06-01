package darwinsquest.view;

import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.graphics.BanionsSpriteFactory;
import darwinsquest.view.graphics.SpriteAnimation;
import darwinsquest.view.sound.GameSoundSystem;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the inventory menu.
 */
public final class InventoryMenuController extends StageInteractive implements Initializable {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private static final Insets FIRST_BANION_LABEL_OFFSETS = new Insets(20, 0, 5, 0);
    private static final Insets OTHER_BANION_LABELS_OFFSETS = new Insets(5, 0, 5, 0);
    @FXML
    private Button backButton;
    @FXML
    private Pagination banionChooser;
    @FXML
    private BorderPane buttonBox;
    @FXML
    private Button chooseButton;
    @FXML
    private BorderPane pane;
    @FXML
    private Label title;

    /**
     * Default constructor.
     * @param manager the {@link StageManager} related to this controller.
     */
    public InventoryMenuController(final StageManager manager) {
        super(manager);
    }

    @FXML
    void onBackButtonAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    @FXML
    void onChooseAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        createBanionSprites();
        JavaFXUtils.initializeBackground(pane, "img/Blue.png");
    }

    private void createBanionSprites() {
        final var spriteFactory = new BanionsSpriteFactory();
        final var sprite = spriteFactory.getBanionSprite("duck", BanionsSpriteFactory.SpriteType.IDLE);
        final var imageView = new ImageView();
        new SpriteAnimation(imageView,
                sprite.getImage(),
                Duration.seconds(1),
                Animation.INDEFINITE,
                sprite.frames(),
                sprite.frames(),
                sprite.width(),
                sprite.height(),
                false).animate();
        banionChooser.setPageFactory(i -> {
            final var name = new Label("Banion 1");
            final var hp = new Label("Hp: 100");
            final var element = new Label("Element: Grass");
            name.setPadding(FIRST_BANION_LABEL_OFFSETS);
            hp.setPadding(OTHER_BANION_LABELS_OFFSETS);
            element.setPadding(OTHER_BANION_LABELS_OFFSETS);
            final var vbox = new VBox(imageView, name, hp, element);
            vbox.setAlignment(Pos.CENTER);
            return vbox;
        });
    }
}
