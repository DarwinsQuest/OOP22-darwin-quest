package darwinsquest.core.world;

import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.IntSupplier;

import darwinsquest.utility.Asserts;

/**
 * Class that represents a simple {@link Board}.
 * The start position is always considered the first.
 */
public class BoardImpl implements Board {

    private final int levels;
    private final IntSupplier supplier;
    private int position;

    /**
     * Default constructor.
     * @param levels the number of levels provided, it has to be a positive value.
     * @param supplier the movement strategy, it has to return always positive values.
     */
    public BoardImpl(final int levels, final IntSupplier supplier) {
        this.levels = Asserts.intMatch(levels, value -> value > 0);
        this.supplier = Objects.requireNonNull(supplier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevels() {
        return levels;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPos() {
        return position + 1;
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
    public OptionalInt move() {
        final var pos = position;
        position = Math.min(position + Asserts.intMatch(supplier.getAsInt(), value -> value > 0), levels);
        return position != pos ? OptionalInt.of(position - pos) : OptionalInt.empty();
    }
}
