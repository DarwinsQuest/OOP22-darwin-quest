package darwinsquest.core;

/**
 * Interface that represents a tile that will prompt a battle.
*/
public interface BattleTile {

    /**
     * Starts the battle.
    */
    void startBattle();

    /**
     * Retrieves the {@link Banion} which is currently deployed in the
     * battle by the provided {@link Entity}.
     * @param entity the owner of the currently deployed banion.
     * @return {@code entity}'s currently deployed banion.
     */
    Banion getCurrentlyDeployedBanion(Entity entity);

}
