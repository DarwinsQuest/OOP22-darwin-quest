package darwinsquest.core.gameobject.element;

import darwinsquest.core.gameobject.GameObject;

/**
 * Interface that represents an Elemental type.
 */
public interface Element extends GameObject {

    /**
     * Checks if the {@code other} element is weaker than this element.
     * If the {@code other} one isn't weaker it doesn't mean that it is inevitably stronger.
     * @param other the {@link Element} to check.
     * @return if the {@code other} is weaker.
     */
    boolean isStronger(Element other);

    /**
     * Checks if the {@code other} element is stronger than this element.
     * If the {@code other} one isn't stronger it doesn't mean that it is inevitably weaker.
     * @param other the {@link Element} to check.
     * @return if the {@code other} is stronger.
     */
    boolean isWeaker(Element other);
}
