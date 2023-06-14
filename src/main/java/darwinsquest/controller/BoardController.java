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
     */
    void startBattle();

    /**
     * Retrieves the last position of the board.
     * @return the last position of the board.
     */
    int getLastPos();
}
