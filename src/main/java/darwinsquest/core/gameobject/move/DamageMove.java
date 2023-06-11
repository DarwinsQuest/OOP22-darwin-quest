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
     * <p>
     * The damage is calculated by considering the affinity with {@code opponentBanion}'s element:
     * <ul>
     *     <li>if the element of the move is stronger than {@code opponentBanion}'s element, than
     *         the base damage of the move is increased by a factor.</li>
     *     <li>if the element of the move is weaker than {@code opponentBanion}'s element, than
     *         the base damage of the move is decreased by a factor.</li>
     *     <li>if none of the previous conditions are met, the damage inflicted by the move remains
     *         its base damage, without modification.</li>
     * </ul>
     * @param playerBanion the banion that performs the move.
     * @param opponentBanion the {@link Banion} on which the move is performed.
     * @return the damage inflicted by the move to {@code opponentBanion}.
     * @see darwinsquest.core.gameobject.element.Element#isStronger(darwinsquest.core.gameobject.element.Element)
     * @see darwinsquest.core.gameobject.element.Element#isWeaker(darwinsquest.core.gameobject.element.Element)
     * @see DamageMove#getDamageMultiplier()
     */
    int computeDamage(Banion playerBanion, Banion opponentBanion);

    /**
     * Retrieves the damage multiplier of the move that increases/reduces its base damage.
     * The multiplier is relative to the affinity between the element of the move and
     * the element of the {@link darwinsquest.core.gameobject.banion.Banion}
     * on which the above-mentioned move is applied.
     * @return the damage multiplier of the move.
     */
    int getDamageMultiplier();

}
