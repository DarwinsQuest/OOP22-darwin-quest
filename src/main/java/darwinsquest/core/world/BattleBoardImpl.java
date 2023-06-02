package darwinsquest.core.world;

import darwinsquest.core.battle.BasicBattleTile;
import darwinsquest.core.battle.BattleTile;
import darwinsquest.core.difficulty.PositiveIntSupplier;
import darwinsquest.core.difficulty.OpponentsFactory;
import darwinsquest.core.gameobject.entity.GameEntity;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;
import java.util.OptionalInt;

/**
 * Class that represents a simple battle {@link Board}.
 */
public final class BattleBoardImpl extends BoardImpl implements BattleBoard {

    private final GameEntity player;
    private final OpponentsFactory difficulty;
    private boolean createBattle;
    private BattleTile battle;

    /**
     * Default constructor.
     * @param levels   the number of levels provided, it has to be a positive value.
     * @param supplier the movement strategy, it has to return always positive values.
     * @param difficulty the difficulty of this battle.
     * @param player the player.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Player must be stored even if mutable in our logic.")
    public BattleBoardImpl(final int levels,
                           final PositiveIntSupplier supplier,
                           final OpponentsFactory difficulty,
                           final GameEntity player) {
        super(levels, supplier);
        this.difficulty = Objects.requireNonNull(difficulty);
        this.player = Objects.requireNonNull(player);
        createBattle = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove() {
        return isBattleWon() && super.canMove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt move() {
        if (!isBattleWon()) {
            return OptionalInt.empty();
        }
        final var result = super.move();
        createBattle = result.isPresent();
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBattleWon() {
        return !createBattle && Objects.nonNull(battle) && battle.isWinner(battle.getPlayer());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean startBattle() {
        if (createBattle) {
            battle = new BasicBattleTile(player, this.difficulty.createOpponent(this, player));
            createBattle = false;
        }
        if (battle.isWinner(battle.getPlayer())) {
            throw new IllegalStateException();
        }
        battle.startBattle();
        return battle.isWinner(battle.getPlayer());
    }
}
