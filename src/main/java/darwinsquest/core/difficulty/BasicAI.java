package darwinsquest.core.difficulty;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.battle.decision.MoveDecision;
import darwinsquest.core.battle.decision.SwapDecision;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.Map;
import java.util.HashMap;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;

/**
 * A basic implementation of the game AI.
 */
public class BasicAI implements AI {

    private static final int MOVE_DECISION_BOUND = 19;
    private static final int SWAP_DECISION_BOUND = 2;
    private final RandomGenerator generator;
    private final Map<Decision, Integer> decisions;

    /**
     * Creates an instance of {@link BasicAI} using the provided {@code seed} to initialize the random number generator.
     * @param seed the random generator's seed.
     */
    public BasicAI(final int seed) {
        generator = new Random(seed);
        this.decisions = new HashMap<>(Map.of(new SwapDecision(), SWAP_DECISION_BOUND, new MoveDecision(), MOVE_DECISION_BOUND));
    }

    /**
     * Default constructor.
     */
    public BasicAI() {
        generator = new Random();
        this.decisions = new HashMap<>(Map.of(new SwapDecision(), SWAP_DECISION_BOUND, new MoveDecision(), MOVE_DECISION_BOUND));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion decideBanionDeployment(final Collection<Banion> banions) {
        return getRandomElement(banions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move decideMoveSelection(final Collection<Move> moves) {
        return getRandomElement(moves);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> decideBanionSwap(final Collection<Banion> banions) {
        final Collection<Banion> remainingBanions = banions.stream().filter(Banion::isAlive).toList();
        if (remainingBanions.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(getRandomElement(remainingBanions));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Decision getDecision() {
        final var value = generator.nextInt(MOVE_DECISION_BOUND + 1);
        return getRelatedKey(decisions, getNearestNumber(value, decisions.values()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(generator, decisions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final BasicAI ai = (BasicAI) obj;
        return this == obj || ai.generator.equals(this.generator) && ai.decisions.equals(this.decisions);
    }

    private int getNearestNumber(final int num, final Collection<Integer> numbers) {
        int diff = Integer.MAX_VALUE; // difference between retVal and num
        Integer retVal = null;
        final Collection<Integer> remainingNumbers = numbers.stream().filter(number -> num <= number).toList();
        for (final int number : remainingNumbers) {
            if (number - num <= diff) {
                retVal = number;
                diff = number - num;
            }
        }
        /*
         * the method will never return null because it is always called with "num" that is less or equal
         * to at least one number of the collection "numbers".
         */
        return retVal;
    }

    private <K, V> K getRelatedKey(final Map<K, V> map, final V value) {
        return map.entrySet().stream().filter(entry -> entry.getValue().equals(value)).findFirst().get().getKey();
    }

    private <T> T getRandomElement(final Collection<T> coll) {
        return coll.stream().toList().get(generator.nextInt(coll.size()));
    }

}
