package darwinsquest.view;

import java.util.Objects;

/**
 * Stage interactive javafx controller.
 */
public class StageInteractive {

    private final StageManager manager;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     */
    protected StageInteractive(final StageManager manager) {
        this.manager = Objects.requireNonNull(manager);
    }

    /**
     * Retrieves the {@link javafx.stage.Stage} manager.
     * @return the {@link javafx.stage.Stage} manager.
     */
    protected final StageManager getManager() {
        return manager;
    }
}
