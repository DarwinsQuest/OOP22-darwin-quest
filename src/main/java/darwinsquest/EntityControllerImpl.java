package darwinsquest;

import darwinsquest.core.gameobject.entity.GameEntity;
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

    /**
     * Constructor for the entity controller.
     * @param entity the game entity.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing a game entity is needed in the controller.")
    public EntityControllerImpl(final GameEntity entity) {
        this.entity = Objects.requireNonNull(entity);
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

}
