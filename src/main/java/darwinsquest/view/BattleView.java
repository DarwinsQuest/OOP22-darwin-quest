package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.controller.BanionController;
import darwinsquest.controller.BattleController;
import darwinsquest.controller.EntityController;
import darwinsquest.controller.MoveController;
import darwinsquest.util.JavaFXUtils;
import darwinsquest.view.graphics.BanionsSpriteFactory;
import darwinsquest.view.graphics.SpriteAnimation;
import darwinsquest.view.sound.GameSoundSystem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the battle scene.
 */
@Description("battle")
public final class BattleView extends ControllerInteractive<BattleController> implements Initializable, BattleInput {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private final Random randomGenerator = new Random();
    @FXML
    private Label banion1Name;
    @FXML
    private Label banion2Name;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button endBtn;
    @FXML
    private Button swapBtn;
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
    @FXML
    private VBox leftVbox;
    @FXML
    private VBox rightVbox;
    @FXML
    private ImageView leftBanion;
    @FXML
    private ImageView rightBanion;

    private final EntityController player;
    private final EntityController opponent;
    private Object selected;
    private Map<String, ImageView> playerSpriteCache;
    private Map<String, ImageView> opponentSpriteCache;
    private BanionController playerBanion;
    private SpriteAnimation playerBanionAnimation;
    private BanionController opponentBanion;
//    private SpriteAnimation opponentBanionAnimation;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the MVC controller.
     * @param player the player.
     * @param opponent the opponent.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Opponent and Player are needed.")
    public BattleView(final View view,
                      final BattleController controller,
                      final EntityController player,
                      final EntityController opponent) {
        super(view, controller);

        this.player = Objects.requireNonNull(player);
        this.opponent = Objects.requireNonNull(opponent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeRandomBackground();
        playerSpriteCache = createSpriteCache(player, true);
        opponentSpriteCache = createSpriteCache(opponent, false);
        this.player.attachSwapBanionObserver((s, arg) ->
            Platform.runLater(() -> {
                renderPlayerBanion(arg);
                final var moves = arg.getMoves().stream()
                    .sorted(Comparator.comparing(MoveController::getName))
                    .toList();
                moveBtn1.setText(moves.get(0).getName());
                moveBtn2.setText(moves.get(1).getName());
                moveBtn3.setText(moves.get(2).getName());
                moveBtn4.setText(moves.get(3).getName());
                playerBanion = arg;
                playerBanion.attachBanionChangedObserver((so, b) ->
                    Platform.runLater(() -> renderPlayerBanion(b)));
            }));
        this.opponent.attachSwapBanionObserver((s, arg) ->
            Platform.runLater(() -> {
                renderOpponentBanion(arg);
                opponentBanion = arg;
                opponentBanion.attachBanionChangedObserver((so, b) ->
                    Platform.runLater(() -> renderOpponentBanion(b)));
            }));
        GameSoundSystem.stopAll();
        GameSoundSystem.playIntroAndMusic("BossIntro.wav", "BossMain.wav");
    }

    private void initializeRandomBackground() {
        final List<String> backgrounds = List.of("Brown", "Gray", "Pink", "Yellow");
        final var bg = backgrounds.get(randomGenerator.nextInt(backgrounds.size()));
        JavaFXUtils.initializeBackground(borderPane, "img/" + bg + ".png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BanionController deployBanion() {
        return player.getInventory().stream()
            .filter(BanionController::isAlive)
            .findFirst()
            .orElseThrow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object selectMoveOrBanion() {
        return selected;
    }

    @FXML
    void onEndAction(final ActionEvent event) {
        GameSoundSystem.stopAll();
        GameSoundSystem.playSfx("WarpJingle.wav");
        Platform.runLater(Platform::exit);
    }

    @FXML
    void onSwapAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = player.getInventory().stream()
                .filter(BanionController::isAlive)
                .filter(b -> !b.equals(playerBanion))
                .findFirst().get();
        this.playerBanion = (BanionController) selected;
        getController().nextTurn();
        Platform.runLater(() -> {
            playerBanionAnimation.stop();
            renderPlayerBanion(playerBanion);
        });
        getController().nextTurn();
    }

    @FXML
    void onMove1Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = playerBanion.getMoves().stream()
            .filter(m -> m.getName().equals(moveBtn1.getText()))
            .findFirst()
            .orElseThrow();
        getController().nextTurn();
        getController().nextTurn();
    }

    @FXML
    void onMove2Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = playerBanion.getMoves().stream()
            .filter(m -> m.getName().equals(moveBtn2.getText()))
            .findFirst()
            .orElseThrow();
        getController().nextTurn();
        getController().nextTurn();
    }

    @FXML
    void onMove3Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = playerBanion.getMoves().stream()
            .filter(m -> m.getName().equals(moveBtn3.getText()))
            .findFirst()
            .orElseThrow();
        getController().nextTurn();
        getController().nextTurn();
    }

    @FXML
    void onMove4Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = playerBanion.getMoves().stream()
            .filter(m -> m.getName().equals(moveBtn4.getText()))
            .findFirst()
            .orElseThrow();
        getController().nextTurn();
        getController().nextTurn();
    }

    private void renderPlayerBanion(final BanionController banion) {
        leftVbox.getChildren().setAll(
                new Label(banion.getName()),
                new Label("Hp: " + banion.getHp()),
                playerSpriteCache.get(banion.getName()));
    }

    private void renderOpponentBanion(final BanionController banion) {
        rightVbox.getChildren().setAll(
                new Label(banion.getName()),
                new Label("Hp: " + banion.getHp()),
                opponentSpriteCache.get(banion.getName()));
    }

    private Map<String, ImageView> createSpriteCache(final EntityController entity, final boolean horizontalFlip) {
        final var spriteFactory = new BanionsSpriteFactory();
        return entity.getInventory().stream()
                .collect(
                        Collectors.toMap(
                                BanionController::getName,
                                banion -> {
                                    final var imageView = new ImageView();
                                    this.playerBanionAnimation = new SpriteAnimation(
                                            imageView,
                                            spriteFactory.getBanionSprite(banion.getName(), BanionsSpriteFactory.SpriteType.IDLE),
                                            Duration.seconds(1),
                                            Animation.INDEFINITE,
                                            horizontalFlip);
                                    this.playerBanionAnimation.play();
                                    imageView.setPreserveRatio(true);
                                    imageView.fitHeightProperty().bind(borderPane.heightProperty().divide(3));
                                    imageView.fitWidthProperty().bind(borderPane.widthProperty().divide(3));
                                    return imageView;
                                }));
    }

}
