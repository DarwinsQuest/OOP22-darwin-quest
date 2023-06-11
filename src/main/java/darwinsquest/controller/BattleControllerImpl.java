package darwinsquest.controller;

import darwinsquest.core.world.BattleBoard;
import darwinsquest.view.BoardView;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Implementation of {@link BattleController}.
 */
public class BattleControllerImpl implements BattleController {

    private final View view;
    private final BattleBoard board;
    private final BoardView boardView;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param board the game board.
     * @param boardView the view representation of the game board.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed in MVC.")
    public BattleControllerImpl(final View view, final BattleBoard board, final BoardView boardView) {
        this.view = view;
        this.board = board;
        this.boardView = boardView;
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
        view.show(view.createVictoryView(boardView));
        boardView.toggleFight(false);
        boardView.toggleMove(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showGameOver() {
        view.show(view.createGameOverView());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextTurnOrGameOver() {
        final var isBattleOver = !nextTurn();
        if (isBattleOver && !board.isBattleWon()) {
            showGameOver();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextTurnOrVictory() {
        final var isBattleOver = !nextTurn();
        if (isBattleOver && board.isBattleWon()) {
            if (board.getPos() == board.getLastPos()) {
                showBossVictory();
            } else {
                showVictory();
            }
        }
    }

    private void showBossVictory() {
        view.show(view.createBossVictoryView());
    }

}
