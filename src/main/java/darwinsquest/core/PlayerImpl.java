package darwinsquest.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class that represents the {@link Player} implementation.
 */
public class PlayerImpl implements Player {

    private final String nickname;
    private final List<Banion> inventory = new LinkedList<>();

    /**
     * The player constructor.
     * @param nickname the player's nickname.
     */
    public PlayerImpl(final String nickname) {
        // todo: add more checks for special characters.
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Player nickname cannot be null or blank.");
        }
        this.nickname = nickname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getNickname() {
        return nickname;
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
    public void updateInventory(final int index, final Banion banion) {
        if (index >= inventory.size()) {
            inventory.add(index, banion);
        }
        inventory.set(index, banion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion deployBanion() {
        throw new UnsupportedOperationException("User input not yet supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move selectMove() {
        throw new UnsupportedOperationException("User input not yet supported.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> swapBanion() {
        throw new UnsupportedOperationException("User input not yet supported.");
    }

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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PlayerImpl player = (PlayerImpl) o;
        return Objects.equals(nickname, player.nickname) && Objects.equals(inventory, player.inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickname, inventory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PlayerImpl{"
                + "nickname='" + nickname + '\''
                + ", inventory=" + inventory + '}';
    }

}
