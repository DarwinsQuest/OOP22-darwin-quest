package darwinsquest.core;

/**
 * Represents normal difficulty.
 */
public final class Normal implements Difficulty {

    private static final int LEVELS = 10;
    private static final int MAX_STEP = 8;

    /**
     * {@inheritDoc}
     */
    @Override
    public Board getBoard() {
        return new BoardImpl(LEVELS, new Die(MAX_STEP));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Normal";
    }
}
