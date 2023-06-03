package darwinsquest;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.generation.BanionFactory;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class hat represents a banions selector controller.
 */
public class SelectBanionControllerImpl implements SelectBanionController {

    private final ControllerManager controller;
    private final Set<Banion> banions = new HashSet<>();
    private final int toSelect;

    /**
     * Default constructor.
     * @param controller the main controller.
     * @param toSelect the number of banions to select.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Main controller is needed to separate concerns.")
    public SelectBanionControllerImpl(final ControllerManager controller, final int toSelect) {
        this.controller = Objects.requireNonNull(controller);
        this.toSelect = toSelect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getToSelect() {
        return toSelect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<BanionController> getBanions() {
        return new BanionFactory().createElements().stream()
            .map(BanionControllerImpl::new)
            .collect(Collectors.toSet());
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
    public boolean selectBanion(final BanionController banion) {
        return banions.add(((BanionWrapper) banion).getBanion());
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
        return banions.size() == toSelect;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean confirm() {
        if (canConfirm()) {
            controller.addPlayerBanions(banions);
            return true;
        }
        return false;
    }
}
