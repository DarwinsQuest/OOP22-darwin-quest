package darwinsquest.controller;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.DamageMove;
import darwinsquest.util.EObservable;
import darwinsquest.util.EObserver;
import darwinsquest.util.ESource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class that represents a banion controller.
 */
public final class BanionControllerImpl implements BanionWrapper {

    private final EObservable<BanionController> eventBanionChanged = new ESource<>();
    private final Banion banion;

    /**
     * Default constructor.
     * @param banion the banion to wrap.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This class is a wrapper of a mutable banion.")
    public BanionControllerImpl(final Banion banion) {
        this.banion = Objects.requireNonNull(banion);
        this.banion.attachBanionChangedObserver((s, a) -> eventBanionChanged.notifyEObservers(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean attachBanionChangedObserver(final EObserver<? super BanionController> observer) {
        return eventBanionChanged.addEObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean detachBanionChangedObserver(final EObserver<? super BanionController> observer) {
        return eventBanionChanged.removeEObserver(observer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return banion.getName();
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
    public Set<DamageMoveController> getMoves() {
        return banion.getMoves().stream()
            .map(m -> new DamageMoveControllerImpl((DamageMove) m))
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return banion.getLevel();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getXp() {
        return banion.getXp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxXp() {
        return banion.getMaxXp();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAttack() {
        return (int) Math.round(banion.getAttack());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDefence() {
        return (int) Math.round(banion.getDefence());
    }

    /**
     * {@inheritDoc}
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "This class is a wrapper of a mutable banion.")
    @Override
    public Banion getBanion() {
        return banion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final var banionController = (BanionControllerImpl) obj;
        return this.banion.equals(banionController.banion);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(banion);
    }

}
