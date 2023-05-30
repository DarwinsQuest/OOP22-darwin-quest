package darwinsquest;

import java.util.Set;

import darwinsquest.core.gameobject.move.Move;
import darwinsquest.config.CustomDeserializer;
import darwinsquest.config.MoveAdapterFactory;

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
    public Set<Move> createElements() {
        return readElements();
    }
}
