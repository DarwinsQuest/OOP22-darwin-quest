package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.controller.BanionController;
import darwinsquest.controller.MoveController;
import darwinsquest.controller.SelectBanionController;
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private static final String AIR = "Air";
    private static final String ELECTRO = "Electro";
    private static final String FIRE = "Fire";
    private static final String GRASS = "Grass";
    private static final String ROCK = "Rock";
    private static final String WATER = "Water";

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

    private final List<BanionController> banions;
    private final BanionsSpriteFactory spriteFactory;
    private final Map<String, ImageView> spriteCache;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the MVC controller.
     */
    public ChooseBanionMenuView(final View view, final SelectBanionController controller) {
        super(view, controller);
        this.banions = getController().getBanions().stream()
            .sorted(Comparator.comparing(BanionController::getElement))
            .toList();
        spriteFactory = new BanionsSpriteFactory();
        spriteCache = banions.stream()
            .collect(
                Collectors.toMap(
                    BanionController::getName,
                    banion -> {
                        final var imageView = new ImageView();
                        new SpriteAnimation(
                            imageView,
                            spriteFactory.getBanionSprite(banion.getName(), BanionsSpriteFactory.SpriteType.IDLE),
                            Duration.seconds(1),
                            Animation.INDEFINITE,
                            false).play();
                        return imageView;
                    }));
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
        updateButtonsState();
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
        final var name = new Label();
        final var hp = new Label();
        final var element = new Label();
        name.setPadding(FIRST_BANION_LABEL_OFFSETS);
        hp.setPadding(OTHER_BANION_LABELS_OFFSETS);
        element.setPadding(OTHER_BANION_LABELS_OFFSETS);
        final var vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        final var labelMoves = new Label("Moves: ");

        banionChooser.setPageFactory(i -> {
            updateButtonsState();

            final var banion = banions.get(i);

            name.setText(banion.getName());
            name.setTextFill(getColourFromElement(banion.getElement()));
            hp.setText("Hp: " + banion.getHp());
            element.setText("Element: " + banion.getElement());

            final List<Label> movesLabels = new ArrayList<>();
            banion.getMoves().stream()
                .sorted(Comparator.comparing(MoveController::getName))
                .forEach(m -> movesLabels.add(new Label(m.getName() + " - damage: " + m.getBaseDamage())));
            movesLabels.forEach(label -> label.setPadding(OTHER_BANION_LABELS_OFFSETS));

            vbox.getChildren().setAll(spriteCache.get(banion.getName()), name, hp, element, labelMoves);
            movesLabels.forEach(label -> vbox.getChildren().add(label));

            return vbox;
        });
    }

    private Paint getColourFromElement(final String element) {
        switch (element) {
            case AIR:
                return Paint.valueOf(String.valueOf(Color.LIGHTCYAN));
            case ELECTRO:
                return Paint.valueOf(String.valueOf(Color.MEDIUMPURPLE));
            case FIRE:
                return Paint.valueOf(String.valueOf(Color.CRIMSON));
            case ROCK:
                return Paint.valueOf(String.valueOf(Color.GREY));
            case GRASS:
                return Paint.valueOf(String.valueOf(Color.GREEN));
            case WATER:
                return Paint.valueOf(String.valueOf(Color.DODGERBLUE));
            default:
                return Paint.valueOf(String.valueOf(Color.BLACK));
        }
    }

}
