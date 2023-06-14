package darwinsquest.core.gameobject.entity;

import darwinsquest.core.battle.decision.Decision;

import java.util.Objects;
import java.util.Optional;

import darwinsquest.core.difficulty.AI;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.banion.Banion;

/**
 * Class that represents the {@link Opponent} implementation.
 */
public class OpponentImpl extends AbstractGameEntity implements Opponent {

    private AI ai;

    /**
     * The opponent constructor.
     * @param nickname the opponent's nickname.
     * @param difficulty the AI difficulty level.
     */
    public OpponentImpl(final String nickname, final AI difficulty) {
        super(nickname);
        ai = Objects.requireNonNull(difficulty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Banion decideDeployBanion() {
        return ai.decideBanionDeployment(getInventory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move selectMove(final Banion banion) {
        return ai.decideMoveSelection(banion.getMoves());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Banion> decideSwapBanion() {
        return ai.decideBanionSwap(getInventory());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Decision getDecision() {
        return ai.getDecision();
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
        if (!super.equals(o)) {
            return false;
        }
        final OpponentImpl opponent = (OpponentImpl) o;
        return Objects.equals(ai, opponent.ai);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ai);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "OpponentImpl{"
                + "nickname='" + getName() + '\''
                + ", inventory=" + getInventory()
                + ", ai=" + ai + '}';
    }

}
