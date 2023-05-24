package darwinsquest.core;

import darwinsquest.core.gameobject.entity.GameEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A basic implementation of {@link BattleTile}.
 */
public class BasicBattleTile implements BattleTile {

    private final List<GameEntity> players;

    /**
     * This constructor creates a new {@link BasicBattleTile} with the
     * provided entities.
     * @param player the entity that starts the battle.
     * @param opponent {@code player}'s opponent.
     */
    public BasicBattleTile(final GameEntity player, final GameEntity opponent) {
        this.players = List.of(Objects.requireNonNull(player), Objects.requireNonNull(opponent));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEntity getPlayer() {
        return this.players.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEntity getOpponent() {
        return this.players.get(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Turn> startBattle() {
        final List<Turn> turns = new ArrayList<>();
        final var firstTurn = new DeployTurnImpl(getPlayer(), getOpponent());
        firstTurn.performAction();
        turns.add(firstTurn);
        final var secondTurn = new DeployTurnImpl(firstTurn);
        secondTurn.performAction();
        turns.add(secondTurn);
        while (nobodyIsOutOfBanions()) {
            final var previousTurn = turns.get(turns.size() - 1);
            final Turn currentTurn;
            if (previousTurn.otherEntityCurrentlyDeployedBanion().get().isAlive()) {
                currentTurn = getEntityOnTurn(previousTurn).getDecision().getAssociatedTurn(previousTurn);
            } else {
                currentTurn = new SwapTurnImpl(previousTurn);
            }
            turns.add(currentTurn);
            currentTurn.performAction();
        }
        return Collections.unmodifiableList(turns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(players);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "BasicBattleTile [players=" + players + "]";
    }

    private boolean nobodyIsOutOfBanions() {
        return players.stream().noneMatch(GameEntity::isOutOfBanions);
    }

    private GameEntity getEntityOnTurn(final Turn previousTurn) {
        return previousTurn.getOtherEntity();
    }

}
