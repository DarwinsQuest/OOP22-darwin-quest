package darwinsquest.controller;

import darwinsquest.util.EObserver;
import java.util.Set;

/**
 * Interface that represent an immutable banion view.
 */
public interface BanionController extends Choosable {

    /**
     * Attaches an observer that is notified when this banion changes.
     * @param observer the observer.
     * @return if the operation was done successfully.
     */
    boolean attachBanionChangedObserver(EObserver<? super BanionController> observer);

    /**
     * Detaches an observer that is notified when this banion changes.
     * @param observer the observer.
     * @return if the operation was done successfully.
     */
    boolean detachBanionChangedObserver(EObserver<? super BanionController> observer);

    /**
     * Retrieves the name.
     * @return the name.
     */
    String getName();

    /**
     * Retrieves the element of belonging.
     * @return the element of belonging.
     */
    String getElement();

    /**
     * Tells if this {@link darwinsquest.core.gameobject.banion.Banion} is alive or not.
     * @return if is alive or not.
     */
    boolean isAlive();

    /**
     * Retrieves moves that can be performed.
     *
     * @return moves that can be performed.
     */
    Set<DamageMoveController> getMoves();

    /**
     * Retrieves the current level value.
     * @return the level value.
     */
    int getLevel();

    /**
     * Provides the life stat amount.
     * @return the life stat amount.
     */
    int getHp();

    /**
     * Provides the full potential life amount.
     * @return the full potential life amount.
     */
    int getMaxHp();

    /**
     * Retrieves the current experience amount.
     * @return the xp amount.
     */
    int getXp();

    /**
     * Retrieves the max xp amount.
     * @return the max xp.
     */
    int getMaxXp();

    /**
     * Retrieves the current attack stat.
     * @return the attack stat.
     */
    int getAttack();

    /**
     * Retrieves the current defence stat.
     * @return the defence stat.
     */
    int getDefence();

}
