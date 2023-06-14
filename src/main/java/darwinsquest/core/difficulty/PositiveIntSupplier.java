package darwinsquest.core.difficulty;

import java.util.function.IntSupplier;

/**
 * A bounded specialization of a {@link IntSupplier} that
 * returns only positive values.
 */
public interface PositiveIntSupplier extends IntSupplier {

    /**
     * Retrieves the max value that can be returned by this supplier.
     * @return the max value that can be returned calling {@link #getAsInt()}.
     */
    int getMaxAsInt();
}
