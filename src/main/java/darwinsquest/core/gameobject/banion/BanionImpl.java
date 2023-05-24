package darwinsquest.core.gameobject.banion;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;
import darwinsquest.utility.Asserts;

/**
 * Class that represents a simple {@link Banion} implementation.
 * The number of moves is bounded at 4.
 * The moves can only be of {@link #getElement()}, or {@link Neutral}.
 */
public final class BanionImpl implements Banion {

    private final UUID id;
    private final Element element;
    private final String name;
    private final Collection<Move> moves;
    private int maxHp;
    private int hp;

    private BanionImpl(final BanionImpl banion) {
        id = UUID.randomUUID();
        element = banion.element;
        moves = new HashSet<>(banion.moves);
        name = banion.name;
        hp = banion.hp;
        maxHp = banion.maxHp;
    }

    /**
     * Default constructor.
     * @param element element of affinity.
     * @param name identifier.
     * @param hp hit points, represents health.
     * @param moves are allowed only 4 moves per {@link Banion}, not more, not less.
     */
    public BanionImpl(final Element element, final String name, final int hp, final Set<Move> moves) {
        id = UUID.randomUUID();
        this.element = Objects.requireNonNull(element);
        this.moves = Asserts.match(moves,
            value -> Objects.nonNull(value)
            && value.size() == NUM_MOVES
            && value.stream().allMatch(this::isMoveAcceptable));
        this.name = Asserts.stringNotNullOrWhiteSpace(name);
        this.hp = Asserts.intMatch(hp, value -> value > MIN_HP);
        maxHp = this.hp;
    }

    private boolean isMoveAcceptable(final Move move) {
        final var element = Objects.requireNonNull(move).getElement();
        return element.equals(getElement()) || element.getClass().equals(Neutral.class);
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
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxHp(final int amount) {
        maxHp = Asserts.intMatch(amount, value -> value > MIN_HP);
        if (hp > maxHp) {
            setHpToMax();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHpToMax() {
        hp = maxHp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseHp(final int amount) {
        hp = Math.min(Asserts.intMatch(hp + amount, value -> value > hp), maxHp);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseHp(final int amount) {
        hp = Math.max(MIN_HP, Asserts.intMatch(hp - amount, value -> value < hp));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return getHp() > MIN_HP;
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
        return Objects.hash(element, name, moves, hp, maxHp);
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
            + ", maxHp = " + getMaxHp()
            + ", hp = " + getHp()
            + ", moves = [" + getMoves().stream().map(Move::toString).collect(Collectors.joining(", ")) + "]]";
    }
}
