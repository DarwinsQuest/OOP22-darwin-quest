package darwinsquest.core.gameobject.move;

import darwinsquest.core.gameobject.GameObject;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.element.Elemental;

/**
 * Interface that represents a move that can be performed in a battle.
 */
public interface Move extends Elemental, GameObject {

    /**
     * Perform the {@link Move} on a banion.
     * @param playerBanion the banion that performs the {@link Move}.
     * @param opponentBanion the banion on which the {@link Move} is applied.
     */
    void perform(Banion playerBanion, Banion opponentBanion);

}
