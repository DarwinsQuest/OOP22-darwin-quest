package darwinsquest;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that represents a banion controller.
 */
public class BanionControllerImpl extends ESource<BanionController> implements BanionController, EObserver<Banion> {

    private final Banion banion;

    /**
     * Default constructor.
     * @param banion the banion to wrap.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This class is a wrapper of a mutable banion.")
    public BanionControllerImpl(final Banion banion) {
        this.banion = Objects.requireNonNull(banion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final ESource<? extends Banion> s, final Banion arg) {
        if (!arg.equals(banion)) {
            throw new IllegalStateException();
        }
        this.notifyEObservers(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getElement() {
        return banion.getElement().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return banion.isAlive();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getMoves() {
        return banion.getMoves().stream()
            .map(Move::getName)
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHp() {
        return banion.getHp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxHp() {
        return banion.getMaxHp();
    }
}
