package darwinsquest.controller;

/**
 * Interface that represents a controller for a board.
 */
public interface BoardController {

    /**
     * Moves inside the board.
     */
    void move();

    /**
     * Starts current battle.
     * @return if battle terminated with player victory.
     */
    boolean startBattle();
}
