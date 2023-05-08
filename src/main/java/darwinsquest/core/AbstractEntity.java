package darwinsquest.core;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Abstract class that represents an abstract {@link Entity}.
 */
public abstract class AbstractEntity implements Entity {

    private final String nickname;
    private final List<Banion> inventory = new LinkedList<>();

    /**
     * Basic constructor.
     * @param nickname the entity's nickname.
     */
    public AbstractEntity(final String nickname) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Entity nickname cannot be null or blank.");
        }
        this.nickname = nickname;
    }

    /**
     * A constructor that allows the usage of a regular expression
     * to evaluate a nickname validity.
     * @param nickname the entity's nickname.
     * @param pattern  the regular expression.
     */
    public AbstractEntity(final String nickname, final Pattern pattern) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Entity nickname cannot be null or blank.");
        }
        final Matcher matcher = pattern.matcher(nickname);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid nickname format.");
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
        if (inventory.stream().anyMatch(b -> b == banion)) {
            return false;
        }
        return inventory.add(banion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToInventory(final Collection<Banion> banions) {
        final var allowedBanions = banions.stream()
                .filter(current -> inventory.stream().noneMatch(b -> b == current))
                .toList();
        return inventory.addAll(allowedBanions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> updateInventory(final Banion oldBanion, final Banion newBanion) {
        if (!inventory.contains(oldBanion)) {
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
        final AbstractEntity entity = (AbstractEntity) o;
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
