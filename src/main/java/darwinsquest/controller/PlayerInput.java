package darwinsquest.controller;

import darwinsquest.core.battle.decision.Decision;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.move.Move;

/**
 * Interface that represents a {@link darwinsquest.core.gameobject.entity.Player} input.
 */
public interface PlayerInput {

    /**
     * Retrieves the {@link Banion} to deploy.
     * @return the {@link Banion} to deploy.
     */
    Banion deployBanion();

    /**
     * Retrieves the {@link Move} selected.
     * @return the {@link Move} selected.
     */
    Move selectMove();

    /**
     * Retrieves the {@link Banion} to deploy.
     * @return the {@link Banion} to deploy.
     */
    Banion swapBanion();

    /**
     * Retrieves the {@link Decision} selected.
     * @return the {@link Decision} selected.
     */
    Decision getDecision();
}
