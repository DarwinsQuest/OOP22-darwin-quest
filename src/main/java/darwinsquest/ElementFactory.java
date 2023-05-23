package darwinsquest;

import java.util.Optional;
import java.util.Set;

import darwinsquest.core.element.Element;
import darwinsquest.json.CustomDeserializer;
import darwinsquest.json.ElementAdapterFactory;

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
    public Optional<Set<Element>> createElements() {
        return readElements();
    }
}
