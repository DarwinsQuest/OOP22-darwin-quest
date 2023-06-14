package darwinsquest.core.gameobject.move;

import java.util.Objects;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.element.Element;

/**
 * A basic implementation of {@link Move}.
 */
public class BasicMove implements DamageMove {

    private static final int DAMAGE_MULTIPLIER = 2;
    private static final int MIN_DAMAGE_INFLICTED = 1;
    private final int baseDamage;
    private final String name;
    private final Element element;

    /**
     * This constructor creates a new {@link BasicMove} with the provided name, damage and element.
     * @param name The name of the {@link BasicMove}.
     * @param baseDamage The damage inflicted by the {@link BasicMove}.
     * @param element The {@link Element} with which the {@link BasicMove} is associated.
     */
    public BasicMove(final int baseDamage, final String name, final Element element) {
        if (baseDamage <= 0) {
            throw new IllegalArgumentException("baseDamage can't be less or equal to zero.");
        }
        this.baseDamage = baseDamage;
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("name can't be null or blank.");
        }
        this.name = name;
        this.element = Objects.requireNonNull(element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void perform(final Banion playerBanion, final Banion opponentBanion) {
        opponentBanion.decreaseHp(computeDamage(playerBanion, opponentBanion));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBaseDamage() {
        return this.baseDamage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int computeDamage(final Banion playerBanion, final Banion opponentBanion) {
        int computedDamage;
        if (this.element.isWeaker(opponentBanion.getElement())) {
            computedDamage = this.baseDamage / DAMAGE_MULTIPLIER + computeDamageFromStats(playerBanion, opponentBanion);
        } else if (this.element.isStronger(opponentBanion.getElement())) {
            computedDamage = this.baseDamage * DAMAGE_MULTIPLIER + computeDamageFromStats(playerBanion, opponentBanion);
        } else {
            computedDamage = this.baseDamage + computeDamageFromStats(playerBanion, opponentBanion);
        }
        if (computedDamage <= 0) {
            computedDamage = MIN_DAMAGE_INFLICTED;
        }
        return computedDamage;
    }

    private int computeDamageFromStats(final Banion playerBanion, final Banion opponentBanion) {
        return (int) Math.round(playerBanion.getAttack()) - (int) Math.round(opponentBanion.getDefence());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDamageMultiplier() {
        return DAMAGE_MULTIPLIER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getElement() {
        return this.element;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name = " + this.getName() + ", element = " + this.getElement()
                + ", base damage = " + this.getBaseDamage() + "]";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(baseDamage, name, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final BasicMove move = (BasicMove) obj;
        return baseDamage == move.baseDamage && name.equals(move.name) && element.equals(move.element);
    }

}
