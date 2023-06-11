package darwinsquest.controller;

import darwinsquest.core.world.BattleBoard;
import darwinsquest.view.BoardView;
import darwinsquest.view.View;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;

/**
 * Class that represents a controller for a board.
 */
public final class BoardControllerImpl implements BoardController {

    private final BattleBoard board;
    private final View view;
    private BoardView boardView;

    /**
     * Default constructor.
     * @param board the board.
     * @param view the MVC view.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed according to MVC.")
    public BoardControllerImpl(final BattleBoard board, final View view) {
        this.board = Objects.requireNonNull(board);
        this.view = Objects.requireNonNull(view);
    }

    /**
     * Sets the view linked to this controller.
     * @param boardView the view linked to this controller.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed according to MVC.")
    public void setBoardView(final BoardView boardView) {
        if (Objects.nonNull(this.boardView)) {
            throw new IllegalStateException();
        }
        this.boardView = Objects.requireNonNull(boardView);
        this.boardView.init(board.getPos(), board.getLevels(), board.getMaxStep(), board.canMove(), !board.canMove());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        if (board.canMove()) {
            board.move();
            boardView.setPos(board.getPos());
            boardView.toggleMove(false);
            boardView.toggleFight(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startBattle() {
        if (!board.isBattleWon()) {
            final var battleView = view.createBattleView(
                new EntityControllerImpl(board.getPlayer()),
                new EntityControllerImpl(board.getOpponent()),
                new BattleControllerImpl(view, board, boardView));
            board.getPlayer().setInput(new PlayerInputImpl(battleView));
            view.show(battleView);
            board.startBattle();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLastPos() {
        return board.getLastPos();
    }

}
