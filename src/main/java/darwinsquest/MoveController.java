package darwinsquest;

/**
 * Interface that represents the controller of a {@link darwinsquest.core.gameobject.move.Move}.
 * It consists of an immutable view of {@link darwinsquest.core.gameobject.move.Move}.
 */
public interface MoveController {

    /**
     * Retrieves a string representing the type of the {@link darwinsquest.core.gameobject.move.Move}.
     * @return the type of the {@link darwinsquest.core.gameobject.move.Move}.
     */
    String getType();

    /**
     * Retrieves the name of the move.
     * @return the name of the move.
     */
    String getName();

}
