package darwinsquest.view;

import java.util.Objects;

/**
 * MVC Controller interactive javafx view.
 * @param <T> the type of the controller.
 */
public class ControllerInteractive<T> {

    private final T controller;

    /**
     * Default constructor.
     * @param controller the controller.
     */
    protected ControllerInteractive(final T controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * Retrieves the controller.
     * @return the controller.
     */
    protected final T getController() {
        return controller;
    }
}
