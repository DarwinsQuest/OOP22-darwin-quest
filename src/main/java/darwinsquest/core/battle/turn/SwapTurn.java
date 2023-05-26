package darwinsquest.core.battle.turn;

import darwinsquest.core.gameobject.banion.Banion;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Optional;

/**
 * Interface that represents a turn in which the {@link darwinsquest.core.gameobject.entity.GameEntity} on turn
 * has decided to swap his currently deployed {@link Banion}.
 */
public interface SwapTurn extends Turn {

    /**
     * Retrieves the swapped {@link Banion} and the new currently deployed {@link Banion}.
     * @return a {@link Pair} composed by the replaced banion and the banion which has replaced the before mentioned one.
     */
    Pair<Banion, Optional<Banion>> getAction();

}
