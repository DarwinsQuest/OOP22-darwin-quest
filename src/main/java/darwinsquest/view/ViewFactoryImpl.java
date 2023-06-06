package darwinsquest.view;

/**
 * Class that represents a factory of views.
 * @param <T> the view type.
 */
public class ViewFactoryImpl<T extends View> implements ViewFactory<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public T create() {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T create(Controller controller) {
        return null;
    }

}
