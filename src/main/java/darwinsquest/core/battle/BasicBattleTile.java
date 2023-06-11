package darwinsquest.core.battle;

import darwinsquest.core.battle.turn.DeployTurnImpl;
import darwinsquest.core.battle.turn.SwapTurnImpl;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.battle.turn.Turn;
import darwinsquest.core.gameobject.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * A basic implementation of {@link BattleTile}.
 */
public class BasicBattleTile implements BattleTile {

    private static final int MIN_XP_BOUND = 5;
    private final List<GameEntity> players;
    private boolean hasBeenDone;
    private GameEntity winner;
    private boolean isPlayerTurn;
    private final List<Turn> battleTurns = new ArrayList<>();

    /**
     * This constructor creates a new {@link BasicBattleTile} with the
     * provided entities.
     * @param player the entity that starts the battle.
     * @param opponent {@code player}'s opponent.
     */
    public BasicBattleTile(final GameEntity player, final GameEntity opponent) {
        this.players = List.of(Objects.requireNonNull(player), Objects.requireNonNull(opponent));
        isPlayerTurn = true; // this field is set to true because the player is always the first to start the battle in our logic
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
    public boolean newBattle() {
        battleTurns.clear(); // if the battle has been done and the player has lost, the battle should be done again
        // so it is not correct to add other turns to the previous ones.
        if (isWinner(getPlayer())) {
            return false;
        }
        final var firstTurn = new DeployTurnImpl(getPlayer(), getOpponent());
        firstTurn.performAction();
        battleTurns.add(firstTurn);
        final var secondTurn = new DeployTurnImpl(firstTurn);
        secondTurn.performAction();
        battleTurns.add(secondTurn);
        return true; // returns true if is possible to start a new battle
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMinXpBound() {
        return MIN_XP_BOUND;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nextTurn() {
        /*
        if (!nobodyIsOutOfBanions()) {
            hasBeenDone = true;
            setWinner();
            return false; // this method returns false if it is not possible to create a new turn, because the battle is finished
        }
         */
        if (isPlayerTurn) {
            if (getPlayer() instanceof Player) {
                playerTurn();
            } else {
                opponentTurn();
            }
            isPlayerTurn = false;
        } else {
            opponentTurn();
            isPlayerTurn = true;
        }
        if (!nobodyIsOutOfBanions()) {
            hasBeenDone = true;
            setWinner();
            assignXp();
            return false;
        }
        return true; // the method returns true if a new turn can be created, and so the battle can continue.
    }

    private void assignXp() {
        final var randomGenerator = new Random();
        getPlayer().getInventory().forEach(b -> b.increaseXp(randomGenerator.nextInt(MIN_XP_BOUND, b.getMaxXp() - 1)));
    }

    private void playerTurn() {
        final Turn previousTurn = getPreviousTurn();
        final Turn currentTurn = getEntityOnTurn(previousTurn).getDecision().getAssociatedTurn(previousTurn);
        battleTurns.add(currentTurn);
        currentTurn.performAction();
    }

    private void opponentTurn() {
        performCurrentTurn(getPreviousTurn());
    }

    private void performCurrentTurn(final Turn previousTurn) {
        final Turn currentTurn;
        if (previousTurn.otherEntityCurrentlyDeployedBanion().get().isAlive()) {
            currentTurn = this.getEntityOnTurn(previousTurn).getDecision().getAssociatedTurn(previousTurn);
        } else {
            currentTurn = new SwapTurnImpl(previousTurn);
        }
        battleTurns.add(currentTurn);
        currentTurn.performAction();
    }

    private Turn getPreviousTurn() {
        return battleTurns.get(battleTurns.size() - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Turn> getBattleTurns() {
        if (hasBeenDone) {
            return Collections.unmodifiableList(battleTurns);
        } else {
            return List.of();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWinner(final GameEntity entity) {
        Objects.requireNonNull(entity);
        if (hasBeenDone) {
            return this.winner.equals(entity);
        } else {
            return false;
        }
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

    private void setWinner() {
        if (hasBeenDone) {
            if (players.stream().filter(GameEntity::isOutOfBanions).count() == 1
                    && players.stream().filter(GameEntity::isOutOfBanions).findFirst().get().equals(getPlayer())) {
                this.winner = getOpponent();
            } else {
                this.winner = getPlayer();
            }
        }
    }
}
