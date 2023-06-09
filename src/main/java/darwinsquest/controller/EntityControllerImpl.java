package darwinsquest.controller;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.util.AbstractEObserver;
import darwinsquest.util.EObservable;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class that represents an abstract {@link EntityController}.
 */
public class EntityControllerImpl implements EntityController {

    private final GameEntity entity;
    private final EObservable<BanionController> banionControllerEObservable = new ESource<>();

    /**
     * Constructor for the entity controller.
     * @param entity the game entity.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing a game entity is needed in the controller.")
    public EntityControllerImpl(final GameEntity entity) {
        this.entity = Objects.requireNonNull(entity);
        this.entity.attachSwapBanionObserver(new AbstractEObserver<>() {
            @Override
            public void updateOperation(ESource<? extends Banion> s, Banion arg) {
                banionControllerEObservable.notifyEObservers(new BanionControllerImpl(arg));
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return entity.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BanionController> getInventory() {
        return entity.getInventory().stream()
                .map(BanionControllerImpl::new)
                .collect(Collectors.toUnmodifiableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToInventory(final BanionController banion) {
        return entity.addToInventory(((BanionControllerImpl) banion).getBanion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addToInventory(final Collection<BanionController> banions) {
        final var effectiveBanions = banions.stream()
                .map(c -> (BanionControllerImpl) c)
                .map(BanionControllerImpl::getBanion)
                .toList();
        return entity.addToInventory(effectiveBanions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BanionController> updateInventory(final BanionController oldBanion, final BanionController newBanion) {
        final var removedBanion = entity.updateInventory(
                ((BanionControllerImpl) oldBanion).getBanion(),
                ((BanionControllerImpl) newBanion).getBanion());
        return removedBanion.map(BanionControllerImpl::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOutOfBanions() {
        return entity.isOutOfBanions();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean attachSwapBanionObserver(final AbstractEObserver<? super BanionController> observer) {
        return banionControllerEObservable.addEObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean detachSwapBanionObserver(final AbstractEObserver<? super BanionController> observer) {
        return banionControllerEObservable.removeEObserver(observer);
    }

}
