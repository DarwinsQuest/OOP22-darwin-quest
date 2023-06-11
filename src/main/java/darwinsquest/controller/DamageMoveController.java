package darwinsquest.controller;

/**
 * Interface that represents a controller of a {@link darwinsquest.core.gameobject.move.DamageMove}.
 * It consists of an immutable view of {@link darwinsquest.core.gameobject.move.DamageMove}.
 */
public interface DamageMoveController extends MoveController {

    /**
     * Retrieves the base damage inflicted by the {@link darwinsquest.core.gameobject.move.DamageMove}.
     * @return the base damage inflicted by the move.
     */
    int getBaseDamage();

    /**
     * Retrieves the damage multiplier of the move that increases/reduces its base damage.
     * @return the damage multiplier of the move
     * @see darwinsquest.core.gameobject.move.DamageMove#getDamageMultiplier()
     */
    int getDamageMultiplier();

}
