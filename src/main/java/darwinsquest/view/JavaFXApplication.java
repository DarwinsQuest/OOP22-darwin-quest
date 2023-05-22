package darwinsquest.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    public void start(final Stage stage) {
        initStage(stage);
        setStartMenu();
        stage.show();
    }

    private void initStage(final Stage stage) {
        this.stage = stage;
        stage.setTitle("DarwinsQuest");
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/green.png"))));
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        stage.setMinHeight(screenSize.getHeight() * MIN_HEIGHT_FACTOR);
        stage.setMinWidth(screenSize.getWidth() * MIN_WIDTH_FACTOR);
    }

    private void setFromFXML(final Object controller, final String name, final Consumer<Parent> consumer) {
        final var fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("layouts/" + name));
        fxmlLoader.setController(controller);

        try {
            consumer.accept(fxmlLoader.load());
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void setSceneFromFXML(final Object controller, final String name) {
        setFromFXML(controller, name, p -> stage.setScene(new Scene(p)));
    }

    private void setPanelFromFXML(final Object controller, final String name) {
        setFromFXML(controller, name, p -> stage.getScene().setRoot(p));
    }

    private void setStartMenu() {
        setSceneFromFXML(new StartMenuController(this), "startmenu.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showStartMenu() {
        setPanelFromFXML(new StartMenuController(this), "startmenu.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showLogin() {
        setPanelFromFXML(new LoginController(this), "login.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDifficulties() {
        setPanelFromFXML(new DifficultiesController(this), "difficultyselector.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBattle() {
        setPanelFromFXML(new BattleController(this), "battle.fxml");
    }
}
