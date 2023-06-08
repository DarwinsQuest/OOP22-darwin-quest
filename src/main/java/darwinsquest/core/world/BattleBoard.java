package darwinsquest.core.world;

import darwinsquest.core.gameobject.entity.Opponent;
import darwinsquest.core.gameobject.entity.Player;

/**
 * Interface that represents a {@link Board} of battle tiles.
 */
public interface BattleBoard extends Board {

    /**
     * Retrieves the {@link Player} that is moving in the {@link Board}.
     * @return the {@link Player} that is moving in the {@link Board}.
     */
    Player getPlayer();

    /**
     * Retrieves the {@link Opponent} that is moving in the {@link Board}.
     * @return the {@link Opponent} that is moving in the {@link Board}.
     */
    Opponent getOpponent();

    /**
     * Checks if battle tile was won by the player.
     * @return if battle tile was won by the player.
     */
    boolean isBattleWon();

    /**
     * Starts the player relative battle, or repeats it if unfinished.
     * @return if the battle has started.
     */
    boolean startBattle();

    /**
     * Forwarder of {@link darwinsquest.core.battle.BattleTile#nextTurn()}.
     * @return if a new turn can be created.
     * @see darwinsquest.core.battle.BattleTile#nextTurn()
     */
    boolean nextTurn();
}
