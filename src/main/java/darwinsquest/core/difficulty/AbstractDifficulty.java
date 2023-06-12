package darwinsquest.core.difficulty;

import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.core.world.BattleBoard;
import darwinsquest.core.world.BattleBoardImpl;
import darwinsquest.util.Asserts;

/**
 * Abstract class that represents this game difficulty.
 */
public abstract class AbstractDifficulty implements Difficulty {

    private final BattleBoard board;

    /**
     * Default constructor.
     * @param minOpponentBanions the min number of opponent Banions.
     * @param maxOpponentBanions the min number of opponent Banions.
     * @param minOpponentBanionsLevel the min possible level of opponent Banions.
     * @param maxOpponentBanionsLevel the max possible level of opponent Banions.
     * @param levels the number of levels.
     * @param maxStep the max step of movement.
     * @param player the player that will fight the opponents.
     * @see Die the random strategy used to step.
     */
    public AbstractDifficulty(final int minOpponentBanions,
                              final int maxOpponentBanions,
                              final int minOpponentBanionsLevel,
                              final int maxOpponentBanionsLevel,
                              final int levels,
                              final int maxStep,
                              final Player player) {
        board = new BattleBoardImpl(Asserts.intMatch(levels, value -> value > 0),
            new Die(Asserts.intMatch(maxStep, value -> value > 0)),
            new OpponentsFactoryImpl(Asserts.intMatch(minOpponentBanions, value -> value > 0),
                Asserts.intMatch(maxOpponentBanions, value -> value > minOpponentBanions),
                Asserts.intMatch(minOpponentBanionsLevel, value -> value > 0),
                Asserts.intMatch(maxOpponentBanionsLevel, value -> value > minOpponentBanionsLevel),
                BasicAI.class),
            player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final BattleBoard getBoard() {
        return board;
    }
}
