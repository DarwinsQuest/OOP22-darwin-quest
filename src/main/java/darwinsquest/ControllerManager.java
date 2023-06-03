package darwinsquest;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.Player;

import java.util.Set;

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

    /**
     * Adds player banions.
     * @param banions the selected banions.
     */
    void addPlayerBanions(Set<Banion> banions);
}
