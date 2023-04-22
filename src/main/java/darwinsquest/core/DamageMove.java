package darwinsquest.core;

/**
 * Interface that represents a {@link Move} that can do damage.
 */
public interface DamageMove extends Move {

    /** 
     * @return the damage caused by the {@link DamageMove}.
    */
    int getDamage();
    
    /*
     * Makes damage to a {@link Banion}.
     * @param banion The {@link Banion} that is attacked with this {@link DamageMove}. 
     *
     * void inflictDamage(Banion banion);
     * This method will be the implementation of the method performMove(Banion banion).
     * The provided documentation will be used to document the implementation 
     * of the above-mentioned method.
    */

}
