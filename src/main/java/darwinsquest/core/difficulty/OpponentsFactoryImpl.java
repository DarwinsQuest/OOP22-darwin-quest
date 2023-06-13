package darwinsquest.core.difficulty;

import com.github.javafaker.Faker;
import darwinsquest.core.gameobject.entity.Opponent;
import darwinsquest.generation.BanionFactory;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import darwinsquest.core.world.Board;
import darwinsquest.util.Asserts;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class that creates opponents.
 */
public final class OpponentsFactoryImpl implements OpponentsFactory {

    private final int minBanionLevel;
    private final int maxBanionLevel;
    private final int minOpponentBanions;
    private final int maxOppBanions;
    private final Class<? extends AI> ai;
    private final Random random = new Random();

    /**
     * Default constructor, with hidden opponents levels.
     * @param minOpponentBanions the minimum amount of opponents.
     * @param maxOppBanions the maximum amount of opponents.
     * @param ai the {@link AI} to create.
     */
    public OpponentsFactoryImpl(final int minOpponentBanions,
                                final int maxOppBanions,
                                final Class<? extends AI> ai) {
        this(minOpponentBanions, maxOppBanions, 1, 10, ai);
    }

    /**
     * Default constructor.
     * @param minOpponentBanions the minimum amount of opponents.
     * @param maxOppBanions the maximum amount of opponents.
     * @param minBanionLevel the min level of opponent's Banions.
     * @param maxBanionLevel the max level of opponent's Banions.
     * @param ai the {@link AI} to create.
     */
    public OpponentsFactoryImpl(final int minOpponentBanions,
                                final int maxOppBanions,
                                final int minBanionLevel,
                                final int maxBanionLevel,
                                final Class<? extends AI> ai) {
        this.minBanionLevel = Asserts.intMatch(minBanionLevel, value -> value > 0);
        this.maxBanionLevel = Asserts.intMatch(maxBanionLevel, value -> value > minBanionLevel);
        this.minOpponentBanions = Asserts.intMatch(minOpponentBanions, value -> value > 0);
        this.maxOppBanions = Asserts.intMatch(maxOppBanions, value -> value > minOpponentBanions);
        this.ai = Objects.requireNonNull(ai);
    }

    private int getMinOpponentBanionsLevel() {
        return minBanionLevel;
    }

    private int getMaxOpponentBanionsLevel() {
        return maxBanionLevel;
    }

    private int getMinOpponentBanions() {
        return minOpponentBanions;
    }

    private int getMaxOpponentBanions() {
        return maxOppBanions;
    }

    private Stream<Banion> selectBanionsAsStream(final List<Banion> banions,
                                                 final GameEntity player,
                                                 final BiPredicate<Banion, Element> predicate) {
        return banions.stream()
            .filter(b ->
                player.getInventory().stream()
                    .map(Elemental::getElement)
                    .anyMatch(e -> predicate.test(b, e)));
    }

    private Collection<Banion> selectBanionsByLevelAsStream(final Board board,
                                                        final List<Banion> banions,
                                                        final GameEntity player,
                                                        final BiPredicate<Banion, Element> predicate) {
        final var result = selectBanionsAsStream(banions, player, predicate)
            .limit(
                Math.max(getMinOpponentBanions(), Math.floorDiv(board.getPos() * getMaxOpponentBanions(), board.getLastPos())))
            .collect(Collectors.toSet());
        if (result.isEmpty()) {
            throw new IllegalStateException();
        }
        return result;
    }

    private Banion createFirstOpponent(final List<Banion> banions, final GameEntity player) {
        return selectBanionsAsStream(banions, player, (b, e) -> b.getElement().isWeaker(e))
            .findAny()
            .orElseThrow();
    }

    private Collection<Banion> createMidOpponent(final Board board,
                                                 final List<Banion> banions,
                                                 final GameEntity player) {
        return selectBanionsByLevelAsStream(board, banions, player, (b, e) -> !b.getElement().isWeaker(e));
    }

    private Collection<Banion> createLastOpponent(final Board board,
                                                  final List<Banion> banions,
                                                  final GameEntity player) {
        return selectBanionsByLevelAsStream(board, banions, player, (b, e) -> b.getElement().isStronger(e));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Opponent createOpponent(final Board board, final GameEntity player) {
        if (player.isOutOfBanions()) {
            throw new IllegalArgumentException("Player should have at least one "
                + Banion.class.getName() + " alive to create a valid opponent.");
        }

        final OpponentImpl opponent;
        try {
            opponent = new OpponentImpl(new Faker().name().firstName(), ai.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }

        final var banions = new ArrayList<>(new BanionFactory().createElements());
        Collections.shuffle(banions);
        final var pos = board.getPos();

        if (pos == board.getFirstPos()) {
            opponent.addToInventory(createFirstOpponent(banions, player));
        } else if (pos == board.getLastPos()) {
            banions.forEach(banion -> banion.evolveToLevel(maxBanionLevel, b -> true));
            opponent.addToInventory(createLastOpponent(board, banions, player));
        } else {
            banions.forEach(banion ->
                banion.evolveToLevel(
                    random.nextInt(getMinOpponentBanionsLevel() + 1, getMaxOpponentBanionsLevel() - 1), b -> true));
            opponent.addToInventory(createMidOpponent(board, banions, player));
        }

        return opponent;
    }
}
