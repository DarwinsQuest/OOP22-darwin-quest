package darwinsquest.core;

/**
 * Interface that represents the first turn of a battle,
 * in which the entities have to deploy their first {@link Banion}.
 */
public interface DeployTurn extends Turn {

    /**
     * Retrieves the {@link Banion} chosen by the {@link Entity} on turn.
     * @return the banion chosen by the entity on turn. 
     */
    Banion getAction();

}
