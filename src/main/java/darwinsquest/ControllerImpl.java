package darwinsquest;

import darwinsquest.graphics.JavaFXScene;
import javafx.application.Application;

/**
 * Class that represents this project controller.
 * This is the startup point of the application.
 */
public final class ControllerImpl implements Controller {

    private static final String PATH_CONFIG = "config/";

    /**
     * Path to {@link darwinsquest.core.element.Element} definitions.
     */
    public static final String PATH_ELEMENTS = PATH_CONFIG + "elements.json";
    /**
     * Path to {@link darwinsquest.core.Move} definitions.
     */
    public static final String PATH_MOVES = PATH_CONFIG + "moves.json";
    /**
     * Path to {@link darwinsquest.core.Banion} definitions.
     */
    public static final String PATH_BANIONS = PATH_CONFIG + "banions.json";

    private ControllerImpl() { }

    /**
     * Application entry-point.
     * @param args arguments
     */
    public static void main(final String[] args) {
        Application.launch(JavaFXScene.class, args);
    }
}
