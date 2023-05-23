package darwinsquest.core;

/**
 * Interface that represents a {@link Move} that can do damage.
 */
public interface DamageMove extends Move {

    /**
     * Retrieves the damage made by the {@link DamageMove}.
     * @return the damage made by the {@link DamageMove}.
     */
    int getDamage();

}
