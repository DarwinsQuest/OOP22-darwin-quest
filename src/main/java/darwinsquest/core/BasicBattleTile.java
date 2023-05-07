package darwinsquest.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A basic implementation of {@link BattleTile}.
 */
public class BasicBattleTile implements BattleTile {

    private final List<Pair<Entity, Banion>> playersAndCurrentBanions;

    /**
     * This constructor creates a new {@link BasicBattleTile} with the
     * provided entities.
     * @param player the entity that starts the battle.
     * @param opponent {@code player}'s opponent.
     */
    public BasicBattleTile(final Entity player, final Entity opponent) {
        this.playersAndCurrentBanions = new ArrayList<>();
        playersAndCurrentBanions.add(new ImmutablePair<>(Objects.requireNonNull(player), null)); 
        playersAndCurrentBanions.add(new ImmutablePair<>(Objects.requireNonNull(opponent), null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startBattle() {
        playersAndCurrentBanions.stream().
            forEach(tuple -> setCurrentlyDeployedBanion(tuple.getLeft(), tuple.getLeft().deployBanion()));
        while (noOneIsOutOfBanions()) {
            for (final var entityAndBanion : playersAndCurrentBanions) {
                final var entityOnTurn = entityAndBanion.getLeft();
                if (!getCurrentlyDeployedBanion(entityOnTurn).isAlive()) {
                    setCurrentlyDeployedBanion(entityOnTurn, entityOnTurn.swapBanion().get());
                } else {
                    final var opponentBanion = getCurrentlyDeployedBanion(entityNotOnTurn(entityOnTurn));
                    entityOnTurn.selectMove(getCurrentlyDeployedBanion(entityOnTurn)).perform(opponentBanion);
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion getCurrentlyDeployedBanion(final Entity entity) {
        return findMatchingTuple(entity).getRight();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(playersAndCurrentBanions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final BasicBattleTile battle = (BasicBattleTile) obj;
        return this.getOpponent().equals(battle.getOpponent());
    }

    private void setCurrentlyDeployedBanion(final Entity entity, final Banion banion) {
        playersAndCurrentBanions.set(playersAndCurrentBanions
            .indexOf(findMatchingTuple(entity)), new ImmutablePair<>(entity, banion));
    }

    private Pair<Entity, Banion> findMatchingTuple(final Entity entity) {
        return playersAndCurrentBanions.stream().filter(tuple -> tuple.getLeft().equals(entity)).toList().get(0);
    }

    private Entity getOpponent() {
        return playersAndCurrentBanions.get(1).getLeft();
    }

    private boolean noOneIsOutOfBanions() {
        return playersAndCurrentBanions.stream().noneMatch(tuple -> tuple.getLeft().isOutOfBanions());
    }

    private Entity entityNotOnTurn(final Entity entityOnTurn) {
        return playersAndCurrentBanions.get((playersAndCurrentBanions.
            indexOf(findMatchingTuple(entityOnTurn)) + 1) % playersAndCurrentBanions.size()).getLeft();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BasicBattleTile [playersAndCurrentBanions=" + playersAndCurrentBanions + "]";
    }

}
