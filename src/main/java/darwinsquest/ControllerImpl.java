package darwinsquest;

import darwinsquest.graphics.JavaFXScene;
import javafx.application.Application;

/**
 * Class that represents this project controller.
 * This is the startup point of the application.
 */
public final class ControllerImpl implements Controller {

    private ControllerImpl() { }

    /**
     * Application entry-point.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXScene.class, args);
    }
}
