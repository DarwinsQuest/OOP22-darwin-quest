package darwinsquest;

import java.util.Set;

import darwinsquest.core.gameobject.element.Element;
import darwinsquest.config.CustomDeserializer;
import darwinsquest.config.ElementAdapterFactory;

/**
 * Factory that generates {@link Element} objects.
 */
public class ElementFactory extends CustomDeserializer<Element> implements TypeFactory<Element> {

    /**
     * Default constructor.
     */
    public ElementFactory() {
        super(Element.class, ElementAdapterFactory.class, ControllerImpl.PATH_ELEMENTS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Element> createElements() {
        return readElements();
    }
}
