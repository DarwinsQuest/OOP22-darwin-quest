package darwinsquest.view;

import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.sound.GameSoundSystem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * View controller for the menu that allows the {@link darwinsquest.core.gameobject.entity.Player}
 * to choose their {@link darwinsquest.core.gameobject.banion.Banion}s at the beginning of the game.
 */
public final class ChooseBanionMenuView implements Initializable {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    /*
    private static final int NUM_BANIONS = 18;
    private static final Insets FIRST_BANION_LABEL_OFFSETS = new Insets(20, 0, 5, 0);
    private static final Insets OTHER_BANION_LABELS_OFFSETS = new Insets(5, 0, 5, 0);
     */
    @FXML
    private Pagination banionChooser;
    @FXML
    private Button chooseButton;
    @FXML
    private BorderPane pane;
    @FXML
    private Label title;

    @FXML
    void onChooseAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        // createBanionSprites();
        JavaFXUtils.initializeBackground(pane, "img/Blue.png");
    }

    /*
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
        banionChooser.setPageCount(NUM_BANIONS);
    }
     */
}
