package darwinsquest.controller;

import darwinsquest.core.gameobject.move.Move;

/**
 * A {@link Move} wrapper class.
 */
interface MoveWrapper extends DamageMoveController {

    /**
     * Retrieves the {@link Move} that wraps.
     * @return the {@link Move} that wraps.
     */
    Move getMove();
}
