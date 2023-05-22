package darwinsquest.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import darwinsquest.core.element.Element;
import darwinsquest.core.element.Neutral;
import darwinsquest.utility.Asserts;

/**
 * Class that represents a simple {@link Banion} implementation.
 * The number of moves is bounded at 4.
 * The moves can only be of {@link #getElement()}, or {@link Neutral}.
 */
public final class BanionImpl implements Banion {

    /**
     * Allowed number of moves.
     */
    public static final int NUM_MOVES = 4;

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
     * Default constructor.
     * @param element element of affinity.
     * @param name identifier.
     * @param hp hit points, represents health.
     * @param moves are allowed only 4 moves per {@link Banion}, not more, not less.
     */
    public BanionImpl(final Element element, final String name, final int hp, final Collection<Move> moves) {
        id = UUID.randomUUID();
        this.element = Objects.requireNonNull(element);
        this.moves = Asserts.match(moves, value -> Objects.nonNull(value)
            && value.size() == NUM_MOVES
            && value.stream().allMatch(this::isMoveAcceptable));
        this.name = Asserts.stringNotNullOrWhiteSpace(name);
        this.hp = Asserts.intMatch(hp, value -> value > 0);
    }

    private boolean isMoveAcceptable(final Move move) {
        return Objects.nonNull(move)
            && (move.getElement().equals(getElement())
                || move.getElement().getClass().equals(Neutral.class));
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
        hp = Asserts.intMatch(amount, value -> value >= 0);
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
    public boolean replaceMove(final Move oldOne, final Move newOne) {
        return isMoveAcceptable(newOne) && moves.remove(oldOne) && moves.add(newOne);
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
        return getClass().getSimpleName() + " [element = " + getElement()
            + ", name = " + getName()
            + ", hp = " + getHp()
            + ", moves = [" + getMoves().stream().map(Move::toString).collect(Collectors.joining(", ")) + "]]";
    }
}
