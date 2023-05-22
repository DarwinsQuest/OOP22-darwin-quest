package darwinsquest.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * View main class.
 */
public final class JavaFXApplication extends Application implements StageManager {

    private static final double MIN_WIDTH_FACTOR = 0.2;
    private static final double MIN_HEIGHT_FACTOR = 0.2;

    // private final Controller controller = new ControllerImpl();

    private Stage stage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        initStage(stage);
        showStartMenu();
        stage.show();
    }

    private void initStage(final Stage stage) {
        this.stage = stage;
        stage.setTitle("DarwinsQuest");
        stage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("img/green.png")));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setMinHeight(screenSize.getHeight() * MIN_HEIGHT_FACTOR);
        stage.setMinWidth(screenSize.getWidth() * MIN_WIDTH_FACTOR);
    }

    private void setSceneFromFXML(final Object controller, final String name) {
        final var fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("layouts/" + name));
        fxmlLoader.setController(controller);

        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showStartMenu() {
        setSceneFromFXML(new StartMenuController(this), "startmenu.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLogin() {
        setSceneFromFXML(new LoginController(this), "login.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDifficulties() {
        setSceneFromFXML(new DifficultiesController(this), "difficultyselector.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBattle() {
        setSceneFromFXML(new BattleController(this), "battle.fxml");
    }
}
