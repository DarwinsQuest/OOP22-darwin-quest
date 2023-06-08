package darwinsquest.generation;

import java.util.Set;

import darwinsquest.core.gameobject.element.Element;
import darwinsquest.config.CustomDeserializer;
import darwinsquest.config.ElementAdapterFactory;

/**
 * Factory that generates {@link Element} objects.
 */
public class ElementFactory extends CustomDeserializer<Element> implements SetFactory<Element> {

    /**
     * Path to {@link darwinsquest.core.gameobject.element.Element} definitions.
     */
    public static final String PATH_ELEMENTS = "config/elements.json";

    /**
     * Default constructor.
     */
    public ElementFactory() {
        super(Element.class, ElementAdapterFactory.class, PATH_ELEMENTS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Element> createElements() {
        return readElements();
    }
}
