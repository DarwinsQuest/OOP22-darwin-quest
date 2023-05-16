package darwinsquest.core.difficulty;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.random.RandomGenerator;

import darwinsquest.core.Banion;
import darwinsquest.core.Move;

/**
 * A basic implementation of the game AI.
 */
class BasicAI implements AI {

    private final RandomGenerator generator;

    /**
     * Default constructor.
     */
    BasicAI() {
        generator = new Random();
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
        final Collection<Banion> remainingBanions = banions.stream().filter(banion -> banion.isAlive()).toList();
        if (remainingBanions.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(getRandomElement(remainingBanions));
        } 
    }

    private <T> T getRandomElement(final Collection<T> coll) {
        return coll.stream().toList().get(generator.nextInt(coll.size()));
    }

}
