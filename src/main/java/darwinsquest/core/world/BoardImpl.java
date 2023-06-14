package darwinsquest.core.world;

import java.util.Objects;
import java.util.OptionalInt;

import darwinsquest.core.difficulty.PositiveIntSupplier;
import darwinsquest.util.Asserts;

/**
 * Class that represents a simple {@link Board}.
 * The start position is always considered the first.
 */
public class BoardImpl implements Board {

    private static final int FIRST_LEVEL = 1;

    private final int levels;
    private final PositiveIntSupplier supplier;
    private int position;

    /**
     * Default constructor.
     * @param levels the number of levels provided, it has to be a positive value.
     * @param supplier the movement strategy, it has to return always positive values.
     */
    public BoardImpl(final int levels, final PositiveIntSupplier supplier) {
        this.levels = Asserts.intMatch(levels, value -> value > 0);
        this.supplier = Objects.requireNonNull(supplier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getFirstPos() {
        return FIRST_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getLastPos() {
        return FIRST_LEVEL + levels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getLevels() {
        return levels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getPos() {
        return position + FIRST_LEVEL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMove() {
        return position < levels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getMaxStep() {
        return supplier.getMaxAsInt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OptionalInt move() {
        final var pos = position;
        position = Math.min(position + supplier.getAsInt(), levels);
        return position != pos ? OptionalInt.of(position - pos) : OptionalInt.empty();
    }
}
