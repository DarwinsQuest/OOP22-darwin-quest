package darwinsquest.core;

import darwinsquest.core.element.Element;
import darwinsquest.core.element.Neutral;
import darwinsquest.utility.Asserts;
import org.apache.commons.collections4.MultiValuedMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class that represents a simple {@link Banion} implementation.
 * The number of moves is bounded at 4.
 * The moves can only be of {@link #getElement()}, or {@link Neutral}.
 */
public final class BanionImpl implements Banion {

    /**
     * A record that stores a {@link Banion}'s statistics.
     * @param level current level.
     * @param hp current hp.
     * @param maxHp current maxHp.
     */
    protected record BanionStats(int level, int hp, int maxHp) {
    }

    private final UUID id;
    private final Element element;
    private final String name;
    private final Collection<Move> moves;
    private final Evolution evolution;
    private int level = 1;
    private int maxHp;
    private int hp;

    private BanionImpl(final BanionImpl banion) {
        id = UUID.randomUUID();
        element = banion.element;
        name = banion.name;
        moves = new HashSet<>(banion.moves);
        evolution = banion.evolution;
        level = banion.level;
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
        evolution = new LinearEvolution();
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
    public int getLevel() {
        return level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseLevel() {
        level = level + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolve(final Predicate<Banion> requirement) {
        return evolution.evolve(this, requirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolveToLevel(final int level, final Predicate<Banion> requirement) {
        if (this.level >= level) {
            throw new IllegalArgumentException("Current level " + "(" + this.level + ") is past or equal to: " + level);
        }
        final var rollbackRecord = new BanionStats(this.level, hp, maxHp);
        boolean lastStatus;
        while (this.level != level) {
            lastStatus = evolve(requirement);
            if (!lastStatus) {
                rollbackStats(rollbackRecord);
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolveToLevel(final int level, final MultiValuedMap<Predicate<Banion>, Integer> requirements) {
        if (requirements.isEmpty()) {
            return false;
        }
        final var sortedLevels = requirements.values().stream().sorted().toList();
        if (sortedLevels.stream().anyMatch(lvl -> Collections.frequency(sortedLevels, lvl) > 1)) {
            throw new IllegalArgumentException("MultiMap cannot have duplicate values.");
        }
        final var lowestLevel = sortedLevels.get(0);
        final var highestLevel = sortedLevels.get(sortedLevels.size() - 1);
        if (highestLevel >= level || lowestLevel != 1 && lowestLevel <= this.level || highestLevel <= this.level) {
            throw new IllegalArgumentException("Illegal instance of MultiMap.");
        }
        populateMissingConditions(requirements, sortedLevels);
        final var rollbackRecord = new BanionStats(this.level, hp, maxHp);
        final var entries = requirements.entries().stream().sorted(Map.Entry.comparingByValue()).toList();
        for (final var e : entries) {
            final var flag = evolve(e.getKey());
            if (!flag) {
                rollbackStats(rollbackRecord);
                return false;
            }
        }
        return true;
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

    private void rollbackStats(final BanionStats record) {
        level = record.level();
        hp = record.hp();
        maxHp = record.maxHp();
    }

    private void populateMissingConditions(final MultiValuedMap<Predicate<Banion>, Integer> map,
                                           final List<Integer> sortedValues) {
        final var last = sortedValues.get(sortedValues.size() - 1);
        for (int current = 1; current <= last; current++) {
            if (!sortedValues.contains(current)) {
                map.put(banion -> true, current);
            }
        }
    }

}
