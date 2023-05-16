package darwinsquest.core.difficulty;

import darwinsquest.annotation.Description;
import darwinsquest.core.Board;
import darwinsquest.core.BoardImpl;
import darwinsquest.core.Die;

/**
 * Represents normal difficulty.
 */
@Description("Normal")
public final class Normal implements Difficulty {

    private static final int LEVELS = 10;
    private static final int MAX_STEP = 8;

    private final Board board;

    /**
     * Default constructor.
     */
    public Normal() {
        board = new BoardImpl(LEVELS, new Die(MAX_STEP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AI getAI() {
        return new BasicAI();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Difficulty: Normal";
    }
}
