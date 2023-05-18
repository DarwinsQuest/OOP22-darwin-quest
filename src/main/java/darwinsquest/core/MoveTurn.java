package darwinsquest.core;


import org.apache.commons.lang3.tuple.Triple;

/**
 * Interface that represents a {@link Turn} in which the {@link Entity} on turn has chosen
 * to perform a move on a {@link Banion}.
 */
public interface MoveTurn extends Turn {

    /**
     * Retrieves the {@link Move} chosen by the {@link Entity} on turn,
     * the {@link Banion} which performed the {@link Move} and the {@link Banion}
     * on which the {@link Move} is performed.
     * @return a triple made of the chosen move, the banion which performs the move
     *         and the banion on which the move is performed.
     */
    Triple<Move, Banion, Banion> getAction();

}
