package darwinsquest.core.gameobject.entity;

import darwinsquest.controller.PlayerInput;

/**
 * Interface that represents the playable {@link GameEntity}.
 */
public interface Player extends GameEntity {

    /**
     * Sets player input.
     * @param input the player input.
     */
    void setInput(PlayerInput input);
}
