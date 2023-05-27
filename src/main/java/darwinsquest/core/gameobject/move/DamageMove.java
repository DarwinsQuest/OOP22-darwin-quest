package darwinsquest.core.gameobject.move;

import darwinsquest.core.gameobject.banion.Banion;

/**
 * Interface that represents a {@link Move} that can do damage.
 */
public interface DamageMove extends Move {

    /**
     * Retrieves the base damage inflicted by the {@link DamageMove}.
     * @return the base damage inflicted by the move.
     */
    int getBaseDamage();

    /**
     * Retrieves the damage inflicted by the {@link DamageMove} to {@code opponentBanion}.
     * The damage is calculated by considering the affinity with {@code opponentBanion}'s element.
     * @param opponentBanion the {@link Banion} on which the move is performed.
     * @return the damage inflicted by the move to {@code opponentBanion}.
     * @see darwinsquest.core.gameobject.element.Element#isStronger(darwinsquest.core.gameobject.element.Element)
     * @see darwinsquest.core.gameobject.element.Element#isWeaker(darwinsquest.core.gameobject.element.Element)
     */
    int computeDamage(Banion opponentBanion);

}
