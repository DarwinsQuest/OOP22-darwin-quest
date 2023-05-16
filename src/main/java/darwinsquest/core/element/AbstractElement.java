package darwinsquest.core.element;

import java.util.Objects;

/**
 * Class that represents a generic {@link Element}.
 */
public class AbstractElement implements Element {

    private final String name;

    /**
     * Default constructor.
     * @param name the name of this {@link Element}.
     */
    protected AbstractElement(final String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj != null && getClass().equals(obj.getClass()) && name.equals(((AbstractElement) obj).name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }
}
