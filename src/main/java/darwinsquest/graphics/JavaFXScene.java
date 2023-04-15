package darwinsquest.graphics;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * View main class.
 */
public final class JavaFXScene extends Application {

    private static final double TITLE_SIZE = 100;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final Label message = new Label("Hello World!"); 
        message.setFont(new Font(TITLE_SIZE));
        stage.setScene(new Scene(message));
        stage.setTitle("DarwinsQuest");
        stage.show();
    }
}
