package darwinsquest;

import darwinsquest.util.EObservable;

import java.util.Set;

/**
 * Interface that represent an immutable banion view.
 */
public interface BanionController extends EObservable<BanionController> {

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
     * @return moves that can be performed.
     */
    Set<String> getMoves();

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
}
