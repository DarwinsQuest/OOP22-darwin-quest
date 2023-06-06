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
     * Retrieves the damage inflicted by the {@link darwinsquest.core.gameobject.move.DamageMove}
     * to {@code opponentBanion}.
     * The damage is calculated by considering the affinity with {@code opponentBanion}'s element.
     * @param opponentBanion the {@link BanionController} on which the move is performed.
     * @return the damage inflicted by the move to {@code opponentBanion}.
     */
    int computeDamage(BanionController opponentBanion);

}
