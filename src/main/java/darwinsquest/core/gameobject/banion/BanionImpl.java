package darwinsquest.core.gameobject.banion;

import darwinsquest.core.evolution.Evolution;
import darwinsquest.core.evolution.LinearEvolution;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.statistic.AttackStat;
import darwinsquest.core.statistic.DefenceStat;
import darwinsquest.core.statistic.HpStat;
import darwinsquest.core.statistic.Statistic;
import darwinsquest.util.Asserts;
import darwinsquest.util.EObservable;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.ArrayList;
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
     * @param attack current atk.
     * @param defence current def.
     */
    public record BanionStats(int level, int hp, int maxHp, double attack, double defence) {
    }

    private static final int MAX_XP = 20;
    private final EObservable<Banion> eventBanionChanged = new ESource<>();
    private final UUID id;
    private final Element element;
    private final String name;
    private final Set<Move> moves;
    private final Evolution evolution;
    private final List<Integer> previousLevels;
    private int level = 1;
    private int xp;
    private int maxHp;
    private final Statistic<Integer> hp;
    private final Statistic<Double> attack;
    private final Statistic<Double> defence;

    private BanionImpl(final BanionImpl banion) {
        id = UUID.randomUUID();
        element = banion.element;
        name = banion.name;
        moves = new HashSet<>(banion.moves);
        evolution = banion.evolution;
        level = banion.level;
        xp = banion.xp;
        hp = new HpStat<>(banion.hp.getValue());
        maxHp = banion.maxHp;
        attack = new AttackStat<>(banion.attack.getValue());
        defence = new DefenceStat<>(banion.defence.getValue());
        previousLevels = new ArrayList<>(banion.previousLevels);
    }

    /**
     * Default constructor.
     * @param element element of affinity.
     * @param name identifier.
     * @param hp hit points, represents health.
     * @param attack attack stat.
     * @param defence defence stat.
     * @param moves are allowed only 4 moves per {@link Banion}, not more, not less.
     */
    public BanionImpl(final Element element,
                      final String name,
                      final int hp,
                      final double attack,
                      final double defence,
                      final Set<Move> moves) {
        if (Double.compare(attack, 0) <= 0 || Double.compare(defence, 0) <= 0) {
            throw new IllegalArgumentException("Attack or Defence must be greater than 0. Given: " + attack + ", " + defence);
        }
        id = UUID.randomUUID();
        this.element = Objects.requireNonNull(element);
        this.moves = new HashSet<>(Asserts.match(moves,
            value -> Objects.nonNull(value)
                && value.size() == NUM_MOVES
                && value.stream().allMatch(this::isMoveAcceptable)));
        this.name = Asserts.stringNotNullOrWhiteSpace(name);
        this.hp = new HpStat<>(Asserts.intMatch(hp, value -> value > MIN_HP));
        maxHp = this.hp.getValue();
        this.attack = new AttackStat<>(attack);
        this.defence = new DefenceStat<>(defence);
        evolution = new LinearEvolution();
        previousLevels = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean attachBanionChangedObserver(final EObserver<? super Banion> observer) {
        return eventBanionChanged.addEObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean detachBanionChangedObserver(final EObserver<? super Banion> observer) {
        return eventBanionChanged.removeEObserver(observer);
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
        return hp.getValue();
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
        if (hp.getValue() != amount) {
            maxHp = Asserts.intMatch(amount, value -> value > MIN_HP);
            if (hp.getValue() > maxHp) {
                setHpToMax();
            }
            eventBanionChanged.notifyEObservers(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHpToMax() {
        if (hp.getValue() != maxHp) {
            hp.setValue(maxHp);
            eventBanionChanged.notifyEObservers(this);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseHp(final int amount) {
        hp.setValue(Math.min(Asserts.intMatch(hp.getValue() + amount, value -> value > hp.getValue()), maxHp));
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseHp(final int amount) {
        hp.setValue(Math.max(MIN_HP, Asserts.intMatch(hp.getValue() - amount, value -> value < hp.getValue())));
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseAttack(final double amount) {
        final var delta = attack.getValue() + amount;
        if (Double.compare(delta, 0) <= 0) {
            throw new IllegalArgumentException();
        }
        attack.setValue(delta);
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseAttack(final double amount) {
        increaseAttack(-amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttack(final double value) {
        attack.setValue(value);
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getAttack() {
        return attack.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseDefence(final double amount) {
        final var delta = defence.getValue() + amount;
        if (Double.compare(delta, 0) <= 0) {
            throw new IllegalArgumentException();
        }
        defence.setValue(delta);
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseDefence(final double amount) {
        increaseDefence(-amount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefence(final double value) {
        defence.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getDefence() {
        return defence.getValue();
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
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getXp() {
        return Asserts.intMatch(xp, value -> value >= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxXp() {
        return MAX_XP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseXp(final int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount cannot be 0 or a negative number: " + amount);
        }
        final var increase = xp + amount;
        if (increase <= MAX_XP) {
            xp = increase;
        } else {
            final var increaseRest = increase - MAX_XP;
            xp = increase - increaseRest;
            evolve(banion -> true);
            xp = increaseRest;
        }
        eventBanionChanged.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolve(final Predicate<Banion> requirement) {
        final var status = evolution.evolve(this, requirement);
        if (status) {
            eventBanionChanged.notifyEObservers(this);
        }
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean evolveToLevel(final int level, final Predicate<Banion> requirement) {
        if (this.level >= level) {
            throw new IllegalArgumentException("Current level " + "(" + this.level + ") is past or equal to: " + level);
        }
        final var rollbackRecord = new BanionStats(this.level, hp.getValue(), maxHp, attack.getValue(), defence.getValue());
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
        final var mapCopy = new ArrayListValuedHashMap<>(requirements);
        var sortedLevels = mapCopy.values().stream().sorted().toList();
        if (hasDuplicates(sortedLevels)) {
            throw new IllegalArgumentException("MultiMap cannot have duplicate values.");
        }
        // Removing past levels if the map is not new and was not cleared.
        if (containsPreviousLevels(mapCopy)) {
            mapCopy.entries().forEach(e -> {
                if (previousLevels.contains(e.getValue())) {
                    mapCopy.remove(e.getKey());
                }
            });
            sortedLevels = mapCopy.values().stream().sorted().toList();
        }
        if (hasGaps(level, sortedLevels)) {
            throw new IllegalArgumentException("MultiMap is missing required values.");
        }
        final var rollbackRecord = new BanionStats(this.level, hp.getValue(), maxHp, attack.getValue(), defence.getValue());
        final var entries = mapCopy.entries().stream().sorted(Map.Entry.comparingByValue()).toList();
        for (final var e : entries) {
            final var flag = evolve(e.getKey());
            if (!flag) {
                rollbackStats(rollbackRecord);
                return false;
            }
        }
        previousLevels.clear();
        previousLevels.addAll(requirements.values());
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
    public Set<Move> getMoves() {
        return Collections.unmodifiableSet(moves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replaceMove(final Move oldOne, final Move newOne) {
        if (isMoveAcceptable(newOne)
                && !moves.contains(newOne)
                && moves.remove(oldOne)
                && moves.add(newOne)) {
            eventBanionChanged.notifyEObservers(this);
            return true;
        }
        return false;
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
        hp.setValue(record.hp());
        maxHp = record.maxHp();
    }

    private boolean hasDuplicates(final List<Integer> levels) {
        return levels.stream().anyMatch(lvl -> Collections.frequency(levels, lvl) > 1);
    }

    private boolean containsPreviousLevels(final MultiValuedMap<Predicate<Banion>, Integer> map) {
        return !previousLevels.isEmpty() && map.values().stream().anyMatch(previousLevels::contains);
    }

    private boolean hasGaps(final int level, final List<Integer> sortedValues) {
        final var lowest = sortedValues.get(0);
        // If given starting point does not match the current level.
        if (lowest != this.level) {
            return true;
        }
        for (int current = lowest; current < level; current++) {
            if (!sortedValues.contains(current)) {
                return true;
            }
        }
        return false;
    }

}
