package darwinsquest.core;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Class that represents the {@link Player} implementation.
 */
public class PlayerImpl extends AbstractEntity implements Player {

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
        super(nickname, Pattern.compile("^[a-zA-Z](?:[a-zA-Z0-9_]*[a-zA-Z0-9])?$"));
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
    public String toString() {
        return "PlayerImpl{"
                + "nickname='" + getName() + '\''
                + ", inventory=" + getInventory() + '}';
    }

}
