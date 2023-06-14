package darwinsquest.view;

import java.util.Objects;

/**
 * MVC Controller interactive javafx view.
 * @param <T> the type of the controller.
 */
public class ControllerInteractive<T> extends BasicView {

    private final T controller;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the controller.
     */
    protected ControllerInteractive(final View view, final T controller) {
        super(view);
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
