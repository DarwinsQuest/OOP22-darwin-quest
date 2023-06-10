package darwinsquest.view;

import darwinsquest.controller.BanionController;
import darwinsquest.controller.Choosable;

/**
 * Interface that represents a battle input.
 */
public interface BattleInput {

    /**
     * Retrieves the deployed banion input.
     * @return the deployed banion input.
     */
    BanionController deployBanion();

    /**
     * Retrieves the decision input, a {@link darwinsquest.controller.MoveController} or a {@link BanionController}.
     * @return a banion move or the banion to deploy (swap with active).
     */
    Choosable selectMoveOrBanion();

}
