package darwinsquest.core.battle.decision;

import java.util.Objects;

/**
 * Class that represents a generic {@link Decision}.
 */
abstract class MiscDecision implements Decision {

    private final String name;

    /**
     * This constructor creates a new {@link MiscDecision} with
     * the provided name.
     * @param name the decision's name.
     */
    protected MiscDecision(final String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name can't be null or blank");
        }
        this.name = name;
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
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final var decision = (MiscDecision) obj;
        return this == obj || this.name.equals(decision.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Decision: " + this.name;
    }

}
