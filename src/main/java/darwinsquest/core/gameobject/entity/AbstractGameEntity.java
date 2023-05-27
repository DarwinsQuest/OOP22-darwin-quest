package darwinsquest.core.gameobject.entity;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.banion.Banion;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Abstract class that represents an abstract {@link GameEntity}.
 */
public abstract class AbstractGameEntity implements GameEntity {

    private final String nickname;
    private final List<Banion> inventory = new LinkedList<>();

    /**
     * Basic constructor.
     * @param nickname the entity's nickname.
     */
    public AbstractGameEntity(final String nickname) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Entity nickname cannot be null or blank.");
        }
        this.nickname = nickname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Banion> getInventory() {
        return Collections.unmodifiableList(inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToInventory(final Banion banion) {
        if (inventory.contains(banion)) {
            return false;
        }
        return inventory.add(banion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToInventory(final Collection<Banion> banions) {
        final var allowedBanions = banions.stream().filter(b -> !inventory.contains(b)).toList();
        return inventory.addAll(allowedBanions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> updateInventory(final Banion oldBanion, final Banion newBanion) {
        if (!inventory.contains(oldBanion) || inventory.contains(newBanion)) {
            return Optional.empty();
        }
        final var index = inventory.indexOf(oldBanion);
        return Optional.of(inventory.set(index, newBanion));
    }

    @Override
    public abstract Banion deployBanion();

    @Override
    public abstract Move selectMove(Banion banion);

    @Override
    public abstract Optional<Banion> swapBanion();

    @Override
    public abstract Decision getDecision();

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOutOfBanions() {
        return inventory.stream().noneMatch(Banion::isAlive);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return nickname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AbstractGameEntity entity = (AbstractGameEntity) o;
        return Objects.equals(nickname, entity.nickname) && Objects.equals(inventory, entity.inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickname, inventory);
    }

}
