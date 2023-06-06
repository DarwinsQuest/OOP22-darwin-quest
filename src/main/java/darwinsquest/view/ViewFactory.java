package darwinsquest.view;

/**
 * Interface that represents a factory of views.
 */
public interface ViewFactory<T extends View> {

    /**
     * Creates a view.
     * @return the view.
     */
    T create();

    /**
     * Creates a view and attaches a controller.
     * @param controller the view controller.
     * @return the view.
     */
    T create(Controller controller);

}
