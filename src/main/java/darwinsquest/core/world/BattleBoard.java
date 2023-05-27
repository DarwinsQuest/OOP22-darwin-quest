package darwinsquest.core.world;

import darwinsquest.core.gameobject.entity.GameEntity;

/**
 * Interface that represents a battle {@link Board}.
 */
public interface BattleBoard extends Board {

    /**
     * Starts the turn relative battle.
     * @param player the player that has to fight an opponent.
     */
    void startBattle(GameEntity player);
}
