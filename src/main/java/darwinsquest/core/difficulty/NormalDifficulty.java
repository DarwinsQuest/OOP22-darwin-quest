package darwinsquest.core.difficulty;

import darwinsquest.annotation.Description;
import darwinsquest.core.gameobject.entity.Player;

/**
 * Class that represents normal difficulty.
 */
@Description("Normal")
public final class NormalDifficulty extends AbstractDifficulty {

    /**
     * The minimum amount of {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    public static final int MIN_OPP_BANIONS = 1;
    /**
     * The maximum number of {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    public static final int MAX_OPP_BANIONS = 3;

    /**
     * The minimum level of opponent's {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    public static final int MIN_OPP_BANIONS_LEVEL = 1;
    /**
     * The maximum level of opponent's {@link darwinsquest.core.gameobject.banion.Banion}s.
     */
    public static final int MAX_OPP_BANIONS_LEVEL = 10;

    private static final int LEVELS = 15;
    private static final int MAX_STEP = 4;

    /**
     * Default constructor.
     * @param player the player.
     */
    public NormalDifficulty(final Player player) {
        super(MIN_OPP_BANIONS,
            MAX_OPP_BANIONS,
            MIN_OPP_BANIONS_LEVEL,
            MAX_OPP_BANIONS_LEVEL,
            LEVELS,
            MAX_STEP,
            player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Difficulty: Normal";
    }
}
