package darwinsquest.controller;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.generation.BanionFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class hat represents a banions selector controller.
 */
public class SelectBanionControllerImpl implements SelectBanionController {

    private final ControllerManager controller;
    private final EntityController entityController;
    private final List<Banion> banions = new ArrayList<>();
    private final int toSelect;

    /**
     * Default constructor.
     * @param controller the main controller.
     * @param entityController the entity controller.
     * @param toSelect the number of banions to select.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Main controller is needed to separate concerns.")
    public SelectBanionControllerImpl(final ControllerManager controller,
                                      final EntityController entityController,
                                      final int toSelect) {
        this.entityController = Objects.requireNonNull(entityController);
        this.controller = Objects.requireNonNull(controller);
        this.toSelect = toSelect;
    }

    private int getToSelect() {
        return toSelect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BanionController> getBanions() {
        return new BanionFactory().createElements().stream()
            .map(BanionControllerImpl::new)
            .collect(Collectors.toUnmodifiableList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelected(final BanionController banion) {
        return banions.contains(((BanionWrapper) banion).getBanion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSelect() {
        return banions.size() < getToSelect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean selectBanion(final BanionController banion) {
        if (banions.size() < getToSelect()) {
            return banions.add(((BanionWrapper) banion).getBanion());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deselectBanion(final BanionController banion) {
        return banions.remove(((BanionWrapper) banion).getBanion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canConfirm() {
        return banions.size() == getToSelect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirm() {
        if (canConfirm()) {
            entityController.addToInventory(banions.stream()
                    .map(BanionControllerImpl::new)
                    .collect(Collectors.toList()));
            controller.selectDifficulty();
            return true;
        }
        return false;
    }
}
