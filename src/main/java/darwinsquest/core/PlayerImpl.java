package darwinsquest.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents the {@link Player} implementation.
 */
public class PlayerImpl implements Player {

    private final String nickname;
    private final List<Banion> inventory = new LinkedList<>();

    /**
     * The player constructor.
     * <p>
     * A nickname must:
     * <ul>
     *     <li>not be null nor blank</li>
     *     <li>start with an alphabetical character</li>
     *     <li>not start with one or more digits</li>
     *     <li>not start nor end with one or more symbols</li>
     *     <li>not start nor end with one or more underscores</li>
     *     <li>not include any whitespaces</li>
     *     <li>not include unicode characters</li>
     * </ul>
     * A nickname may include:
     * <ul>
     *     <li>one or more underscores between the first and last characters</li>
     *     <li>digits after the first character</li>
     * </ul>
     * @param nickname the player's nickname.
     */
    public PlayerImpl(final String nickname) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Player nickname cannot be null or blank.");
        }
        final Pattern allowedPattern = Pattern.compile("^[a-zA-Z](?:[a-zA-Z0-9_]*[a-zA-Z0-9])?$");
        final Matcher matcher = allowedPattern.matcher(nickname);
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
    public Move selectMove(final Banion banion) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return nickname;
    }

}
