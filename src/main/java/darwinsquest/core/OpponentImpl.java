package darwinsquest.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Class that represents the {@link Opponent} implementation.
 */
public class OpponentImpl implements Opponent {

    private final String nickname;
    private final List<Banion> inventory = new LinkedList<>();
    private AI ai;

    /**
     * The opponent constructor.
     * @param nickname the opponent's nickname.
     * @param difficulty the AI difficulty level.
     */
    public OpponentImpl(final String nickname, final AI difficulty) {
        if (Objects.isNull(nickname) || nickname.isBlank()) {
            throw new IllegalArgumentException("Opponent nickname cannot be null or blank.");
        }
        this.nickname = nickname;
        ai = Objects.requireNonNull(difficulty);
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
        return ai.decideBanionDeployment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move selectMove() {
        return ai.decideMoveSelection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> swapBanion() {
        return ai.decideBanionSwap();
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
    public String getName() {
        return nickname;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDifficulty(final AI difficulty) {
        ai = Objects.requireNonNull(difficulty);
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
        final OpponentImpl opponent = (OpponentImpl) o;
        return Objects.equals(nickname, opponent.nickname)
                && Objects.equals(inventory, opponent.inventory)
                && Objects.equals(ai, opponent.ai);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(nickname, inventory, ai);
    }

}
