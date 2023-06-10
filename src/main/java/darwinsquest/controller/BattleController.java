package darwinsquest.controller;

/**
 * Interface that represents a controller for {@link darwinsquest.core.battle.BattleTile}.
 */
public interface BattleController {

    /**
     * Creates a new battle turn and lets the entity on turn to perform their action.
     * @return if the new turn can be created. A new turn cannot be created if one of the two entities
     *         that are fighting in the battle is out of banions.
     */
    boolean nextTurn();

    /**
     * Shows the victory view when the player wins the battle.
     */
    void showVictory();

    /**
     * Shows a game over view when the player loses the battle.
     */
    void showGameOver();

}
