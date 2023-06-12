package darwinsquest.view;

import darwinsquest.controller.BattleController;
import darwinsquest.controller.BoardController;
import darwinsquest.controller.DifficultyController;
import darwinsquest.controller.EntityController;
import darwinsquest.controller.LoginController;
import darwinsquest.controller.SelectBanionController;

/**
 * Interface of a general view.
 */
public interface View {

    /**
     * Sets username in title.
     * @param username the username to set.
     */
    void setWindowTitlePrefix(String username);

    /**
     * Shows a view.
     * @param view the view to show.
     */
    void show(Object view);

    /**
     * Creates a login view.
     * @param controller the MVC controller associated.
     * @return a login view.
     */
    Object createLoginView(LoginController controller);

    /**
     * Creates a banion selector view.
     * @param controller the MVC controller associated.
     * @return a selection view.
     */
    Object createBanionSelectorView(SelectBanionController controller);

    /**
     * Creates a difficulty selector view.
     * @param controller the MVC controller associated.
     * @return a difficulty view.
     */
    Object createDifficultySelectorView(DifficultyController controller);

    /**
     * Creates a board view.
     * @param controller the MVC controller associated.
     * @return a board view.
     */
    BoardView createBoardView(BoardController controller);

    /**
     * Creates a battle view.
     * @param player the player.
     * @param opponent the opponent.
     * @param controller the controller associated to the view
     * @return the battle view.
     */
    BattleInput createBattleView(EntityController player, EntityController opponent, BattleController controller);

    /**
     * Creates a victory view.
     * @param view the view representation of the game board.
     * @return the victory view.
     */
    Object createVictoryView(BoardView view);

    /**
     * Creates a game-over view.
     * @return the game-over view.
     */
    Object createGameOverView();

    /**
     * Creates the view that is shown at the end of the game, when the final boss
     * has been defeated by the player.
     * @return the final view of the game.
     */
    Object createBossVictoryView();

}
