package darwinsquest.view;

import darwinsquest.Controller;

import java.util.Objects;

/**
 * Stage and MVC Controller interactive javafx controller.
 */
public class ControllerStageInteractive extends StageInteractive {

    private final Controller controller;

    /**
     * Default constructor.
     * @param manager the stage manager related to this javafx controller.
     * @param controller the MVC controller.
     */
    protected ControllerStageInteractive(final StageManager manager, final Controller controller) {
        super(manager);
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * Retrieves the {@link Controller}.
     * @return the {@link Controller}.
     */
    protected final Controller getController() {
        return controller;
    }
}
