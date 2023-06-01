package darwinsquest.generation;

import java.util.Set;

import darwinsquest.core.gameobject.move.Move;
import darwinsquest.config.CustomDeserializer;
import darwinsquest.config.MoveAdapterFactory;

/**
 * Factory that generates {@link Move} objects.
 */
public class MoveFactory extends CustomDeserializer<Move> implements SetFactory<Move> {

    /**
     * Path to {@link darwinsquest.core.gameobject.move.Move} definitions.
     */
    public static final String PATH_MOVES = "config/moves.json";

    /**
     * Default constructor.
     */
    public MoveFactory() {
        super(Move.class, MoveAdapterFactory.class, PATH_MOVES);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Move> createElements() {
        return readElements();
    }
}
