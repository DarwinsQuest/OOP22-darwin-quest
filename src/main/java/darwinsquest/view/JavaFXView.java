package darwinsquest.view;

import darwinsquest.BoardController;
import darwinsquest.Controller;
import darwinsquest.ControllerImpl;
import darwinsquest.DifficultyController;
import darwinsquest.LoginController;
import darwinsquest.annotation.Description;
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
public final class JavaFXView extends Application implements View {

    private static final double MIN_WIDTH_FACTOR = 0.2;
    private static final double MIN_HEIGHT_FACTOR = 0.2;

    private static final String TITLE = "Darwin's Quest";
    private static final String SEPARATOR = " - ";

    private Controller controller;
    private Stage stage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) {
        controller = new ControllerImpl(this);
        initStage(stage);
        setStartMenuView();
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

    private void setFromFXML(final Object controller, final Consumer<Parent> consumer) {
        final var fxmlLoader = new FXMLLoader(
            ClassLoader.getSystemResource(
                "layouts/"
                + controller.getClass().getAnnotation(Description.class).value()
                + ".fxml"));
        fxmlLoader.setController(controller);

        try {
            consumer.accept(fxmlLoader.load());
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setWindowTitlePrefix(final String username) {
        stage.setTitle(Objects.requireNonNull(username) + SEPARATOR + TITLE);
    }

    private void setSceneFromFXML(final Object controller) {
        setFromFXML(controller, p -> stage.setScene(new Scene(p)));
    }

    private void setParentFromFXML(final Object controller) {
        setFromFXML(controller, p -> stage.getScene().setRoot(p));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void show(final Object view) {
        setParentFromFXML(view);
    }

    private void setStartMenuView() {
        setSceneFromFXML(new StartMenuView(controller));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createLoginView(final LoginController controller) {
        return new LoginView(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createDifficultySelectorView(final DifficultyController controller) {
        return new DifficultiesSelectorView(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BoardView createBoardView(final BoardController controller) {
        return new BoardViewImpl(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object createBattleView() {
        return new BattleView(controller);
    }
}
