package darwinsquest.view;

import darwinsquest.BanionController;
import darwinsquest.SelectBanionController;
import darwinsquest.annotation.Description;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

/**
 * View controller for the menu that allows the {@link darwinsquest.core.gameobject.entity.Player}
 * to choose their {@link darwinsquest.core.gameobject.banion.Banion}s at the beginning of the game.
 */
@Description("choosebanionmenu")
public final class ChooseBanionMenuView extends ControllerInteractive<SelectBanionController>
    implements Initializable {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private static final Insets FIRST_BANION_LABEL_OFFSETS = new Insets(20, 0, 5, 0);
    private static final Insets OTHER_BANION_LABELS_OFFSETS = new Insets(5, 0, 5, 0);

    @FXML
    private Pagination banionChooser;
    @FXML
    private Button btSelect;
    @FXML
    private Button btDeselect;
    @FXML
    private Button btConfirm;
    @FXML
    private BorderPane pane;
    @FXML
    private Label title;

    private final List<BanionController> banions;

    /**
     * Default constructor.
     * @param controller the MVC controller.
     */
    public ChooseBanionMenuView(final SelectBanionController controller) {
        super(controller);
        this.banions = getController().getBanions().stream()
            .sorted(Comparator.comparing(BanionController::getName))
            .toList();
    }

    private void updateButtonsState() {
        if (getController().isSelected(banions.get(banionChooser.getCurrentPageIndex()))) {
            btSelect.setDisable(true);
            btDeselect.setDisable(false);
        } else {
            btSelect.setDisable(!getController().canSelect());
            btDeselect.setDisable(true);
        }
        btConfirm.setDisable(!getController().canConfirm());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(pane, "img/Blue.png");
        banionChooser.setPageCount(banions.size());
        createBanionSprites();
        btConfirm.setDisable(!getController().canConfirm());
    }

    @FXML
    private void onSelectAction() { // NOPMD, events are indirectly used
        GameSoundSystem.playSfx(BUTTON_SOUND);
        getController().selectBanion(banions.get(banionChooser.getCurrentPageIndex()));
        updateButtonsState();
    }

    @FXML
    private void onDeselectAction() { // NOPMD, events are indirectly used
        GameSoundSystem.playSfx(BUTTON_SOUND);
        getController().deselectBanion(banions.get(banionChooser.getCurrentPageIndex()));
        updateButtonsState();
    }

    @FXML
    private void onConfirmAction() { // NOPMD, events are indirectly used
        GameSoundSystem.playSfx(BUTTON_SOUND);
        getController().confirm();
    }

    private void createBanionSprites() {
        final var spriteFactory = new BanionsSpriteFactory();

        banionChooser.setPageFactory(i -> {
            updateButtonsState();

            final var banion = banions.get(i);
            final var sprite = spriteFactory.getBanionSprite(banion.getName(), BanionsSpriteFactory.SpriteType.IDLE);

            final var name = new Label(banion.getName());
            final var hp = new Label("Hp: " + banion.getHp());
            final var element = new Label("Element: " + banion.getElement());
            name.setPadding(FIRST_BANION_LABEL_OFFSETS);
            hp.setPadding(OTHER_BANION_LABELS_OFFSETS);
            element.setPadding(OTHER_BANION_LABELS_OFFSETS);

            final var imageView = new ImageView();
            new SpriteAnimation(
                imageView,
                sprite,
                Duration.seconds(1),
                Animation.INDEFINITE,
                false).play();

            final var vbox = new VBox(imageView, name, hp, element);
            vbox.setAlignment(Pos.CENTER);

            return vbox;
        });
    }
}
