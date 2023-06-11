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

    /**
     * Creates a new battle turn and allows the opponent to perform their action.
     * If the player is out of banions, shows the game over view.
     */
    void nextTurnOrGameOver();

    /**
     * Creates a new battle turn and allows the player to perform their action.
     * If the opponent is out of banions, shows the victory view.
     */
    void nextTurnOrVictory();

}
