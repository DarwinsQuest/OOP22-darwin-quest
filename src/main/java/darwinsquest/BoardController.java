package darwinsquest;

import darwinsquest.view.BoardView;

/**
 * Interface that represents a controller for a board.
 */
public interface BoardController {

    /**
     * Sets the view linked to this controller.
     * @param view th view linked to this controller.
     */
    void init(BoardView view);

    /**
     * Moves inside the board.
     */
    void move();
}
