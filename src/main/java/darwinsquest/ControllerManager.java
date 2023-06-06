package darwinsquest;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.Player;

import java.util.Set;

/**
 * Interface that represents a manager of controllers.
 */
public interface ControllerManager extends MainController {

    /**
     * Sets the player.
     * @param player the player.
     */
    void setPlayer(Player player);

    /**
     * Selects first player Banions.
     */
    void selectFirstPlayerBanions();

    /**
     * Adds player banions.
     * @param banions the selected banions.
     */
    void addPlayerBanions(Set<Banion> banions);

    /**
     * Selects the difficulty.
     */
    void selectDifficulty();

    /**
     * Starts the board.
     */
    void startBoard();
}
