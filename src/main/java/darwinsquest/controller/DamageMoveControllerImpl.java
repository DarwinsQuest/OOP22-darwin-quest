package darwinsquest.controller;

import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.move.DamageMove;
import darwinsquest.core.gameobject.move.Move;

/**
 * Implementation of {@link DamageMoveController}.
 */
public class DamageMoveControllerImpl implements MoveWrapper {

    private final DamageMove move;

    /**
     * Default constructor.
     * @param move the {@link DamageMove} to show in the view.
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
    public int getDamageMultiplier() {
        return move.getDamageMultiplier();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Element getElement() {
        return move.getElement();
    }

}
