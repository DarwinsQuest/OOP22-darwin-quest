package darwinsquest.core.world;

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
     * @return if the player won the battle.
     */
    boolean startBattle();
}
