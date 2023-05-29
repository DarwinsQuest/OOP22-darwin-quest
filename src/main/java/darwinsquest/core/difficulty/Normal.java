package darwinsquest.core.difficulty;

import darwinsquest.annotation.Description;
import darwinsquest.core.world.BattleBoard;
import darwinsquest.core.world.BattleBoardImpl;

/**
 * Represents normal difficulty.
 */
@Description("Normal")
public final class Normal implements Difficulty {

    private static final int LEVELS = 10;
    private static final int MAX_STEP = 8;

    private final BattleBoard board;

    /**
     * Default constructor.
     */
    public Normal() {
        board = new BattleBoardImpl(LEVELS,
            new Die(MAX_STEP),
            new OpponentsFactoryImpl(MIN_OPP_BANIONS, MAX_OPP_BANIONS, BasicAI.class));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BattleBoard getBoard() {
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
