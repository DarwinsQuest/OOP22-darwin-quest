package darwinsquest.view;

import darwinsquest.Controller;
import darwinsquest.ControllerImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * View main class.
 */
public final class JavaFXApplication extends Application implements StageManager {

    private static final double MIN_WIDTH_FACTOR = 0.2;
    private static final double MIN_HEIGHT_FACTOR = 0.2;

    private static final String TITLE = "darwin's quest";
    private static final String SEPARATOR = " - ";

     private final Controller controller = new ControllerImpl();

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
        stage.setTitle(TITLE);
        stage.getIcons().add(new Image(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("img/icon.png"))));
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
    public void setUsername(final String username) {
        stage.setTitle(Objects.requireNonNull(username) + SEPARATOR + TITLE);
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
        setPanelFromFXML(new LoginController(this, controller), "login.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showDifficulties() {
        setPanelFromFXML(new DifficultiesController(this, controller), "difficultyselector.fxml");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBattle() {
        setPanelFromFXML(new BattleController(this), "battle.fxml");
    }
}
