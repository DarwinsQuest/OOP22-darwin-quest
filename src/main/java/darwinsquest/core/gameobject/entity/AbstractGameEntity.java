package darwinsquest.core.gameobject.entity;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.util.EObservable;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;

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

    private final EObservable<Banion> eventSwap = new ESource<>();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public final Banion deployBanion() {
        final var banion = decideDeployBanion();
        eventSwap.notifyEObservers(banion);
        return banion;
    }

    /**
     * Retrieves the {@link GameEntity}'s chosen {@link Banion}
     * to be their active battle companion.
     * @return the chosen Banion.
     */
    public abstract Banion decideDeployBanion();

    @Override
    public abstract Move selectMove(Banion banion);

    /**
     * {@inheritDoc}
     */
    @Override
    public final Optional<Banion> swapBanion() {
        final var banion = decideSwapBanion();
        banion.ifPresent(eventSwap::notifyEObservers);
        return banion;
    }

    /**
     * Swaps the active battle {@link Banion} with
     * another available one chosen by the {@link GameEntity}.
     * @return an {@link Optional} containing a {@link Banion} if any is left,
     *         empty otherwise.
     */
    public abstract Optional<Banion> decideSwapBanion();

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
    public boolean attachSwapBanionObserver(final EObserver<? super Banion> observer) {
        return eventSwap.addEObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean detachSwapBanionObserver(final EObserver<? super Banion> observer) {
        return eventSwap.removeEObserver(observer);
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
