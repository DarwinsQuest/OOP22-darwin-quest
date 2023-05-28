package darwinsquest.view;

import darwinsquest.json.BanionsSpriteDeserializer;
import darwinsquest.view.graphics.Sprite;
import darwinsquest.view.graphics.SpriteAnimation;
import darwinsquest.view.sound.GameSoundSystem;
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
import java.util.ResourceBundle;

/**
 * Controller for the battle scene.
 */
public final class BattleController extends StageInteractive implements Initializable {

    private static final double WIDTH = 0.3;
    private static final double HEIGHT = 0.3;
    private static final int BG_UPSCALE = 4;
    private static final Sprite ANGRY_PIG = new BanionsSpriteDeserializer()
        .getBanionSprite("angrypig", BanionsSpriteDeserializer.SpriteType.IDLE);
    private static final Image IMAGE1 = ANGRY_PIG.getImage();
//    private static final int FRAMES_IMG1 = 9;
//    private static final int WIDTH_IMG1 = 36;
//    private static final int HEIGHT_IMG1 = 30;
    private static final Image IMAGE2 = new Image("img/banions/Bat/Flying (46x30).png");
    private static final int FRAMES_IMG2 = 7;
    private static final int WIDTH_IMG2 = 46;
    private static final int HEIGHT_IMG2 = 30;
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

    /**
     * Default constructor.
     *
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
        new SpriteAnimation(
                leftBanion,
                IMAGE1,
                Duration.seconds(1),
                Animation.INDEFINITE,
                ANGRY_PIG.frames(),
                ANGRY_PIG.frames(),
                ANGRY_PIG.width(),
                ANGRY_PIG.height(),
                true).animate();
        new SpriteAnimation(
                rightBanion,
                IMAGE2,
                Duration.seconds(1),
                Animation.INDEFINITE,
                FRAMES_IMG2,
                FRAMES_IMG2,
                WIDTH_IMG2,
                HEIGHT_IMG2,
                false).animate();
        GameSoundSystem.stopAll();
        GameSoundSystem.playIntroAndMusic("BossIntro.wav", "BossMain.wav");
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
    void onMove1Action(final ActionEvent event) {
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

}
