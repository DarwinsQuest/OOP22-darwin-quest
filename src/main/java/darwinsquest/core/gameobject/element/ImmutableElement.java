package darwinsquest.core.gameobject.element;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a generic {@link Element}.
 */
public class ImmutableElement implements Element {

    private final Set<String> weaker;
    private final Set<String> stronger;
    private final String name;

    /**
     * Default constructor.
     * @param name the name.
     */
    protected ImmutableElement(final String name) {
        this.name = Objects.requireNonNull(name);
        weaker = Set.of();
        stronger = Set.of();
    }

    /**
     * Constructor with {@code weaker} and {@code stronger} elements.
     * @param name the name.
     * @param weaker the name of the {@code weaker} elements.
     * @param stronger the name of the {@code stronger} elements.
     */
    public ImmutableElement(final String name, final Set<String> weaker, final Set<String> stronger) {
        this.name = Objects.requireNonNull(name);
        this.weaker = Collections.unmodifiableSet(Objects.requireNonNull(weaker));
        this.stronger = Collections.unmodifiableSet(Objects.requireNonNull(stronger));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStronger(final Element other) {
        return weaker.contains(other.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWeaker(final Element other) {
        return stronger.contains(other.getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, weaker, stronger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj != null && getClass().equals(obj.getClass()) && name.equals(((ImmutableElement) obj).name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return name;
    }
}
