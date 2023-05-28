package darwinsquest.core.world;

import darwinsquest.core.gameobject.entity.GameEntity;

/**
 * Interface that represents a {@link Board} of battle tiles.
 */
public interface BattleBoard extends Board {

    /**
     * Checks if battle tile was won by the player.
     * @return if battle tile was won by the player.
     */
    boolean isBattleWon();

    /**
     * Starts the player relative battle, or repeats it if unfinished.
     * @param player the player that has to play the battle.
     */
    void startBattle(GameEntity player);
}
