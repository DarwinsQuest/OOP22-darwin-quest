package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.controller.BanionController;
import darwinsquest.controller.BattleController;
import darwinsquest.controller.Choosable;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
import java.util.stream.IntStream;

/**
 * Controller for the battle scene.
 */
@Description("battle")
public final class BattleView extends ControllerInteractive<BattleController> implements Initializable, BattleInput {

    private static final String BUTTON_SOUND = "MI_SFX21.wav";
    private final Random randomGenerator = new Random();
    @FXML
    private Label title;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button endBtn;
    @FXML
    private Button swapBtn;
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
    private Choosable selected;
    private Map<String, ImageView> playerSpriteCache;
    private Map<String, ImageView> opponentSpriteCache;
    private BanionController playerBanion;
    private SpriteAnimation playerBanionAnimation;
    private BanionController opponentBanion;
    private SpriteAnimation opponentBanionAnimation;

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
        setBattleUsernames();
        initializeRandomBackground();
        playerSpriteCache = createSpriteCache(player, true);
        opponentSpriteCache = createSpriteCache(opponent, false);
        this.player.attachSwapBanionObserver((s, arg) ->
            Platform.runLater(() -> {
                renderBanion(leftVbox, playerSpriteCache, arg);
                final var moves = arg.getMoves().stream()
                    .sorted(Comparator.comparing(MoveController::getName))
                    .toList();
                final var moveButtons = List.of(moveBtn1, moveBtn2, moveBtn3, moveBtn4);
                IntStream.range(0, moveButtons.size())
                        .forEach(i -> {
                            final var btn = moveButtons.get(i);
                            final var move = moves.get(i);
                            btn.setText(move.getName());
                            Tooltip.install(btn, new Tooltip("Base DMG: " + move.getBaseDamage()));
                        });
                playerBanion = arg;
                playerBanion.attachBanionChangedObserver((so, b) ->
                    Platform.runLater(() -> renderBanion(leftVbox, playerSpriteCache, b)));
            }));
        this.opponent.attachSwapBanionObserver((s, arg) ->
            Platform.runLater(() -> {
                renderBanion(rightVbox, opponentSpriteCache, arg);
                opponentBanion = arg;
                opponentBanion.attachBanionChangedObserver((so, b) ->
                    Platform.runLater(() -> renderBanion(rightVbox, opponentSpriteCache, arg)));
            }));
        GameSoundSystem.stopAll();
        playRandomBGM();
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
    public Choosable selectMoveOrBanion() {
        return selected;
    }

    @FXML
    void onEndAction(final ActionEvent event) {
        GameSoundSystem.stopAll();
        getController().showGameOver();
    }

    @FXML
    void onSwapAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        // Disables the swap button if the player has only one banion left.
        if (player.getInventory().stream().filter(BanionController::isAlive).count() == 1) {
            swapBtn.setDisable(true);
        }
        selected = player.getInventory().stream()
                .filter(BanionController::isAlive)
                .filter(b -> !b.equals(playerBanion))
                .findFirst().get();
        this.playerBanion = (BanionController) selected;
        getController().nextTurn(); // the player performs a swap, so the opponent's banion cannot die.
        Platform.runLater(() -> renderBanion(leftVbox, playerSpriteCache, playerBanion));
        disableMoveButtons(false);
        getController().nextTurnOrGameOver();
        disableMoveButtonsIfDead();
    }

    @FXML
    void onMove1Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        playerMoveAction(moveBtn1);
    }

    @FXML
    void onMove2Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        playerMoveAction(moveBtn2);
    }

    @FXML
    void onMove3Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        playerMoveAction(moveBtn3);
    }

    @FXML
    void onMove4Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        playerMoveAction(moveBtn4);
    }

    private void playerMoveAction(final Button moveButton) {
        selected = playerBanion.getMoves().stream()
                .filter(m -> m.getName().equals(moveButton.getText()))
                .findFirst()
                .orElseThrow();
        // The player performs its turn. If the opponent is out of banions, then the victory view is shown.
        getController().nextTurnOrVictory();
        // The opponent performs its turn. If the player is out of banions, then the game-over view is shown.
        getController().nextTurnOrGameOver();
        disableMoveButtonsIfDead();
    }

    private void playRandomBGM() {
        final List<String> tracks = List.of("BossMain.wav", "Mars.wav", "Mercury.wav", "Venus.wav");
        final var bgm = tracks.get(randomGenerator.nextInt(tracks.size()));
        if ("BossMain.wav".equals(bgm)) {
            GameSoundSystem.playIntroAndMusic("BossIntro.wav", bgm);
        } else {
            GameSoundSystem.playMusic(bgm, true);
        }
    }

    private void renderBanion(final VBox vBox, final Map<String, ImageView> sprites, final BanionController banion) {
        vBox.getChildren().setAll(
                new Label(banion.getName()),
                new Label("LVL: " + banion.getLevel()),
                new Label("HP: " + banion.getHp()),
                new Label("XP: " + banion.getXp() + "/" + banion.getMaxXp()),
                new Label("ATK: " + banion.getAttack()),
                new Label("DEF: " + banion.getDefence()),
                sprites.get(banion.getName())
        );
    }

    private Map<String, ImageView> createSpriteCache(final EntityController entity, final boolean horizontalFlip) {
        final var spriteFactory = new BanionsSpriteFactory();
        return entity.getInventory().stream()
                .collect(
                        Collectors.toMap(
                                BanionController::getName,
                                banion -> {
                                    final var imageView = new ImageView();
                                    final var animation = new SpriteAnimation(
                                            imageView,
                                            spriteFactory.getBanionSprite(banion.getName(), BanionsSpriteFactory.SpriteType.IDLE),
                                            Duration.seconds(1),
                                            Animation.INDEFINITE,
                                            horizontalFlip);
                                    if (entity.isPlayer()) {
                                        playerBanionAnimation = animation;
                                        playerBanionAnimation.play();
                                    } else {
                                        opponentBanionAnimation = animation;
                                        opponentBanionAnimation.play();
                                    }
                                    imageView.setPreserveRatio(true);
                                    imageView.fitHeightProperty().bind(borderPane.heightProperty().divide(3));
                                    imageView.fitWidthProperty().bind(borderPane.widthProperty().divide(3));
                                    return imageView;
                                }));
    }

    private void disableMoveButtons(final boolean value) {
        List.of(moveBtn1, moveBtn2, moveBtn3, moveBtn4).forEach(button -> button.setDisable(value));
    }

    private void disableMoveButtonsIfDead() {
        if (!playerBanion.isAlive()) {
            disableMoveButtons(true);
        }
    }

    private void setBattleUsernames() {
        title.setText(player.getName() + " VS " + opponent.getName());
    }

}
