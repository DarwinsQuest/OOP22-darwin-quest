package darwinsquest;

import java.util.Optional;
import java.util.Set;

import darwinsquest.core.Move;
import darwinsquest.json.CustomDeserializer;
import darwinsquest.json.MoveAdapterFactory;

/**
 * Factory that generates {@link Move} objects.
 */
public class MoveFactory extends CustomDeserializer<Move> implements TypeFactory<Move> {

    /**
     * Default constructor.
     */
    public MoveFactory() {
        super(Move.class, MoveAdapterFactory.class, ControllerImpl.PATH_MOVES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Set<Move>> createElements() {
        return readElements();
    }
}
