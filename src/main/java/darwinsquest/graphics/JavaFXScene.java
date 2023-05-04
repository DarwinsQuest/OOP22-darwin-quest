package darwinsquest.graphics;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View main class.
 */
public final class JavaFXScene extends Application {

    private static final double MIN_WIDTH_FACTOR = 0.2;
    private static final double MIN_HEIGHT_FACTOR = 0.2;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Parent root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/startmenu.fxml"));

        stage.setMinHeight(screenSize.getHeight() * MIN_HEIGHT_FACTOR);
        stage.setMinWidth(screenSize.getWidth() * MIN_WIDTH_FACTOR);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
