package darwinsquest.view;

import darwinsquest.annotation.Description;
import darwinsquest.controller.BanionController;
import darwinsquest.controller.BattleController;
import darwinsquest.controller.EntityController;
import darwinsquest.controller.MoveController;
import darwinsquest.util.AbstractEObserver;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;
import darwinsquest.view.graphics.BanionsSpriteFactory;
import darwinsquest.view.graphics.Sprite;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Controller for the battle scene.
 */
@Description("battle")
public final class BattleView extends ControllerInteractive<BattleController> implements Initializable, BattleInput {

    private static final double WIDTH = 0.3;
    private static final double HEIGHT = 0.3;
    private static final int BG_UPSCALE = 4;
    private static final Sprite SPRITE1 = new BanionsSpriteFactory()
        .getBanionSprite("turtle", BanionsSpriteFactory.SpriteType.IDLE);
    private static final Image IMAGE1 = SPRITE1.getImage();
    private static final Sprite SPRITE2 = new BanionsSpriteFactory()
        .getBanionSprite("duck", BanionsSpriteFactory.SpriteType.IDLE);
    private static final Image IMAGE2 = SPRITE2.getImage();
    private static final String BUTTON_SOUND = "MI_SFX21.wav";

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
    private BanionController opponentBanion;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeBackground();

        final var spriteFactory = new BanionsSpriteFactory();
        playerSpriteCache = player.getInventory().stream()
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
                            true).play();
                        imageView.fitHeightProperty().bind(borderPane.heightProperty().divide(3));
                        imageView.fitWidthProperty().bind(borderPane.widthProperty().divide(3));
                        return imageView;
                    }));
        opponentSpriteCache = opponent.getInventory().stream()
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
                        imageView.fitHeightProperty().bind(borderPane.heightProperty().divide(3));
                        imageView.fitWidthProperty().bind(borderPane.widthProperty().divide(3));
                        return imageView;
                    }));

        this.player.attachSwapBanionObserver(new AbstractEObserver<>() {
            @Override
            public void updateOperation(ESource<? extends BanionController> s, BanionController arg) {
                renderPlayerBanion(arg);
                final var moves = arg.getMoves().stream()
                        .sorted(Comparator.comparing(MoveController::getName))
                        .toList();
                Platform.runLater(() -> {
                    moveBtn1.setText(moves.get(0).getName());
                    moveBtn2.setText(moves.get(1).getName());
                    moveBtn3.setText(moves.get(2).getName());
                    moveBtn4.setText(moves.get(3).getName());
                });
                System.out.println(arg);
                playerBanion = arg;
                playerBanion.attachBanionChangedObserver(new AbstractEObserver<BanionController>() {
                    @Override
                    public void updateOperation(ESource<? extends BanionController> s, BanionController arg) {
                        Platform.runLater(() -> renderPlayerBanion(arg));
                    }
                });
            }
        });
        this.opponent.attachSwapBanionObserver(new AbstractEObserver<>() {
            @Override
            public void updateOperation(ESource<? extends BanionController> s, BanionController arg) {
                renderPlayerBanion(arg);
                opponentBanion = arg;
                opponentBanion.attachBanionChangedObserver(new AbstractEObserver<>() {
                    @Override
                    public void updateOperation(ESource<? extends BanionController> s, BanionController arg) {
                        Platform.runLater(() -> renderOpponentBanion(arg));
                    }
                });
            }
        });
        GameSoundSystem.stopAll();
        GameSoundSystem.playIntroAndMusic("BossIntro.wav", "BossMain.wav");
    }

    private void initializeBackground() {
        final Image img = new Image("img/bg.png");
        final BackgroundSize bgSize = new BackgroundSize(
            WIDTH,
            HEIGHT,
            true,
            true,
            true,
            false);
        final BackgroundImage bgImg = new BackgroundImage(
            new Image(img.getUrl(),
                img.getWidth() * BG_UPSCALE,
                img.getHeight() * BG_UPSCALE,
                true,
                false),
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            bgSize
        );
        final Background bg = new Background(bgImg);
        borderPane.setBackground(bg);
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
    void onForfeitAction(final ActionEvent event) {
        GameSoundSystem.stopAll();
        GameSoundSystem.playSfx("WarpJingle.wav");
        Platform.runLater(Platform::exit);
    }

    @FXML
    void onInventoryAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
        selected = player.getInventory().stream()
            .filter(BanionController::isAlive)
            .findAny()
            .orElseThrow();
        // getController().nextTurn();
        // getController().nextTurn();
        // synchronizer.signal();
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
}
