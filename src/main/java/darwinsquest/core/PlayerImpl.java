package darwinsquest.core;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
    public Inventory getInventory() {
        throw new UnsupportedOperationException("Inventory has yet to be implemented.");
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
     * todo: remove once the interface Inventory is implemented.
     * @return the player's Inventory.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Updating the field from outside is needed.")
    public List<Banion> getInventoryAsList() {
        return inventory;
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
