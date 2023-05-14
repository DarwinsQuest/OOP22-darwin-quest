package darwinsquest.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import darwinsquest.core.element.Element;
import darwinsquest.core.element.Neutral;
import darwinsquest.utility.Assert;

/**
 * Class that represents a simple {@link Banion} implementation.
 */
public final class BanionImpl implements Banion {

    private final UUID id;
    private final Element element;
    private final String name;
    private final Collection<Move> moves;
    private int hp;

    private BanionImpl(final BanionImpl banion) {
        id = banion.id;
        moves = banion.moves.stream().map(Move::copy).collect(Collectors.toSet());
        name = banion.name;
        hp = banion.hp;
        element = banion.element;
    }

    /**
     * Constructor that creates a {@link Banion} with a provided hit points amount.
     * @param element element of affinity.
     * @param name identifier.
     * @param hp hit points, represents health.
     */
    public BanionImpl(final Element element, final String name, final int hp) {
        id = UUID.randomUUID();
        moves = new HashSet<>();
        this.name = Assert.stringNotNullOrWhiteSpace(name);
        this.hp = Assert.intMatch(hp, value -> value > 0);
        this.element = element;
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
     */
    @Override
    public void setHp(final int amount) {
        hp = Assert.intMatch(amount, value -> value >= 0);
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
    public Banion copy() {
        return new BanionImpl(this);
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
            && id.equals(((BanionImpl) obj).id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BanionImpl [element=" + getElement() + ", name=" + getName() + "]";
    }
}
