package darwinsquest.core;

import darwinsquest.core.element.Element;

/**
 * Represents an object that has an {@link Element} link.
 * @see Element
 */
public interface Elemental {

    /**
     * Retrieves an {@link Element}.
     * @return the {@link Element}.
     */
    Element getElement();
}
