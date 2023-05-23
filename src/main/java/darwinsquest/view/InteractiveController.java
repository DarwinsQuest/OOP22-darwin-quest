package darwinsquest.view;

import java.util.Objects;

/**
 * Abstract fxml controller.
 */
public class InteractiveController {

    private final StageManager manager;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    protected InteractiveController(final StageManager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    /**
     * Retrieves the {@link javafx.stage.Stage} manager.
     * @return the {@link javafx.stage.Stage} manager.
     */
    protected StageManager getManager() {
        return manager;
    }
}
