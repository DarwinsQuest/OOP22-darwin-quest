package darwinsquest.controller;

import darwinsquest.core.gameobject.move.DamageMove;
import darwinsquest.core.gameobject.move.Move;

/**
 * Implementation of {@link DamageMoveController}.
 */
public class DamageMoveControllerImpl implements MoveWrapper {

    private final DamageMove move;

    /**
     * Default constructor.
     * @param move the {@link darwinsquest.core.gameobject.move.DamageMove} to show in the view.
     */
    public DamageMoveControllerImpl(final DamageMove move) {
        this.move = move;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBaseDamage() {
        return this.move.getBaseDamage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int computeDamage(final BanionController opponentBanion) {
        return this.move.computeDamage(((BanionControllerImpl) opponentBanion).getBanion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType() {
        return this.move.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.move.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Move getMove() {
        return move;
    }
}
