package darwinsquest.controller;

import darwinsquest.core.world.BattleBoard;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of {@link BattleController}.
 */
public class BattleControllerImpl implements BattleController {

    private final View view;
    private final BattleBoard board;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param board the game board.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed in MVC.")
    public BattleControllerImpl(final View view, final BattleBoard board) {
        this.view = view;
        this.board = board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean nextTurn() {
        return board.nextTurn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showVictory() {
        view.show(view.createVictoryView());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOver() {
        view.show(view.createGameOverView());
    }

}
