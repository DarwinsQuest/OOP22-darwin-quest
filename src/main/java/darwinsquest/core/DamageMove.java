package darwinsquest.core;

/**
 * Interface that represents a {@link Move} that can do damage.
 */
public interface DamageMove extends Move {

    /**
     * Retrieves the damage of the {@link DamageMove}.
     * @return the damage of the {@link DamageMove}.
     */
    int getDamage();

}
