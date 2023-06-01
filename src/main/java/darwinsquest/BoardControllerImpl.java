package darwinsquest;

import darwinsquest.core.world.BattleBoard;
import darwinsquest.view.BoardView;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;

/**
 * Class that represents a controller for a board.
 */
public class BoardControllerImpl implements BoardController {

    private final BattleBoard board;
    private BoardView view;

    /**
     * Default constructor.
     * @param board the board.
     */
    public BoardControllerImpl(final BattleBoard board) {
        this.board = Objects.requireNonNull(board);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "View is needed according to MVC.")
    @Override
    public void init(final BoardView view) {
        if (Objects.nonNull(this.view)) {
            throw new IllegalStateException();
        }
        this.view = Objects.requireNonNull(view);
        this.view.init(board.getPos(), board.getLevels(), board.getMaxStep(), board.canMove(), !board.canMove());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move() {
        if (board.canMove()) {
            board.move();
            view.setPos(board.getPos());
            view.toggleMove(false);
            view.toggleFight(true);
        }
    }
}
