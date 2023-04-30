package darwinsquest.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.IntPredicate;

import darwinsquest.core.element.Element;
import darwinsquest.core.element.Neutral;

/**
 * Class that represents a simple {@link Banion} implementation.
 */
public class BanionImpl implements Banion {

    private final Element element;
    private final String name;
    private final Collection<Move> moves;
    private int hp;

    /**
     * Costructor that creates a {@link Banion} with a provided hit points amount.
     * @param element element of affinity.
     * @param name identifier.
     * @param hp hit points, represents health.
     * @throws IllegalArgumentException If hit points init to negative or zero.
     */
    public BanionImpl(final Element element, final String name, final int hp) {
        assertIntLegalArgument(hp, value -> value > 0, "Banion hp can't be init to a negative value or zero.");
        moves = new HashSet<>();
        this.name = Objects.requireNonNull(name);
        this.hp = hp;
        this.element = element;
    }

    private void assertIntLegalArgument(final int stat, final IntPredicate predicate, final String message) {
        if (predicate.negate().test(stat)) {
            throw new IllegalArgumentException(message);
        }
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
    public Element getElement() {
        return element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHp() {
        return hp;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If hit points set to negative.
     */
    @Override
    public void setHp(final int amount) {
        assertIntLegalArgument(amount, value -> value >= 0, "Banion hp can't be set to a negative value.");
        hp = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<Move> getMoves() {
        return Collections.unmodifiableCollection(moves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean learnMove(final Move move) {
        return (move.getElement().equals(element) || move.getElement().getClass().equals(Neutral.class)) && moves.add(move);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean forgetMove(final Move move) {
        return moves.remove(move);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(element, name, moves, hp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return this == obj || obj != null
            && getClass().equals(obj.getClass())
            && name.equals(((BanionImpl) obj).name)
            && element.equals(((BanionImpl) obj).element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BanionImpl [element=" + getElement() + ", name=" + getName() + "]";
    }
}
