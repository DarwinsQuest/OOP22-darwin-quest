package darwinsquest.controller;

import darwinsquest.core.world.BattleBoard;

/**
 * Implementation of {@link BattleController}.
 */
public class BattleControllerImpl implements BattleController {

    private final BattleBoard board;

    /**
     * Default constructor.
     * @param board the game board.
     */
    public BattleControllerImpl(final BattleBoard board) {
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nextTurn() {
        return board.nextTurn();
    }

}
