package darwinsquest.core.battle.turn;


import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;
import org.apache.commons.lang3.tuple.Triple;

/**
 * Interface that represents a {@link Turn} in which the {@link darwinsquest.core.gameobject.entity.GameEntity} on turn
 * has chosen to perform a move on a {@link Banion}.
 */
public interface MoveTurn extends Turn {

    /**
     * Retrieves the {@link Move} chosen by the {@link darwinsquest.core.gameobject.entity.GameEntity} on turn,
     * the {@link Banion} which performed the {@link Move} and the {@link Banion}
     * on which the {@link Move} is performed.
     * @return a {@link Triple} made of the chosen move, the banion which performs the move
     *         and the banion on which the move is performed.
     */
    Triple<Move, Banion, Banion> getAction();

}
