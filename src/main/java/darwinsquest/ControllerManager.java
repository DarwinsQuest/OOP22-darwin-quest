package darwinsquest;

import darwinsquest.core.gameobject.entity.Player;

/**
 * Interface that represents a manager of controllers.
 */
public interface ControllerManager extends Controller {

    /**
     * Sets the player.
     * @param player the player.
     */
    void setPlayer(Player player);

    /**
     * Sets the board.
     */
    void setBoard();
}
