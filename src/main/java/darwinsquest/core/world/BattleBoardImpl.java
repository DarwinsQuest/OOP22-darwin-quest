package darwinsquest.core.world;

import darwinsquest.core.battle.BasicBattleTile;
import darwinsquest.core.battle.BattleTile;
import darwinsquest.core.difficulty.OpponentsFactory;
import darwinsquest.core.gameobject.entity.GameEntity;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntSupplier;

/**
 * Class that represents a simple battle {@link Board}.
 */
public class BattleBoardImpl extends BoardImpl implements BattleBoard {

    private final OpponentsFactory difficulty;
    private boolean createBattle;
    private BattleTile battle;

    /**
     * Default constructor.
     * @param levels   the number of levels provided, it has to be a positive value.
     * @param supplier the movement strategy, it has to return always positive values.
     * @param difficulty the difficulty of this battle.
     */
    public BattleBoardImpl(final int levels, final IntSupplier supplier, final OpponentsFactory difficulty) {
        super(levels, supplier);
        this.difficulty = Objects.requireNonNull(difficulty);
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
    public void startBattle(final GameEntity player) {
        if (createBattle) {
            battle = new BasicBattleTile(player, this.difficulty.createOpponent(this, player));
            createBattle = false;
        }
        battle.startBattle();
    }
}
