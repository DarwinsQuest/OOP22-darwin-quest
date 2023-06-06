package darwinsquest.view;

import darwinsquest.controller.BanionController;
import darwinsquest.controller.Controller;
import darwinsquest.controller.EntityController;
import darwinsquest.annotation.Description;
import darwinsquest.util.Synchronizer;
import darwinsquest.view.graphics.BanionsSpriteFactory;
import darwinsquest.view.graphics.Sprite;
import darwinsquest.view.graphics.SpriteAnimation;
import darwinsquest.view.sound.GameSoundSystem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the battle scene.
 */
@Description("battle")
public final class BattleView extends ControllerInteractive<Controller> implements Initializable, BattleInput {

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
    private ImageView leftBanion;
    @FXML
    private ImageView rightBanion;

    private final EntityController player;
//    private final EntityController opponent;
    private final Synchronizer synchronizer;
//    private Object selected;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the MVC controller.
     * @param player the player.
//     * @param opponent the opponent.
     * @param playerInputSynchronizer the synchronizer.
     */

    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Opponent and Player are needed.")
    public BattleView(final View view,
                      final Controller controller,
                      final EntityController player,
                      /*final EntityController opponent,*/
                      final Synchronizer playerInputSynchronizer) {
        super(view, controller);
        this.player = Objects.requireNonNull(player);
//        this.opponent = Objects.requireNonNull(opponent);
        this.synchronizer = Objects.requireNonNull(playerInputSynchronizer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        initializeBackground();
        new SpriteAnimation(
                leftBanion,
                IMAGE1,
                Duration.seconds(1),
                Animation.INDEFINITE,
                SPRITE1.frames(),
                SPRITE1.frames(),
                SPRITE1.width(),
                SPRITE1.height(),
                true).play();
        new SpriteAnimation(
                rightBanion,
                IMAGE2,
                Duration.seconds(1),
                Animation.INDEFINITE,
                SPRITE2.frames(),
                SPRITE2.frames(),
                SPRITE2.width(),
                SPRITE2.height(),
                false).play();
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
        try {
            // waits here until signal is sent
            synchronizer.waitForSignal();
        } catch (final InterruptedException e) {
            throw new IllegalStateException(e);
        }
        throw new IllegalStateException("Implementation must go on!");
//        return selected;
    }

    @FXML
    void onForfeitAction(final ActionEvent event) {
        GameSoundSystem.stopAll();
        GameSoundSystem.playSfx("WarpJingle.wav");
    }

    @FXML
    void onInventoryAction(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    @FXML
    void onMove1Action(final ActionEvent event) throws InterruptedException {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    @FXML
    void onMove2Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    @FXML
    void onMove3Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }

    @FXML
    void onMove4Action(final ActionEvent event) {
        GameSoundSystem.playSfx(BUTTON_SOUND);
    }
}
