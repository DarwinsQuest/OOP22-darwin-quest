package darwinsquest.core;

/**
 * Interface that represents an in-game
 * consumable object.
 */
@FunctionalInterface
public interface Item {

    /**
     * Consumes the selected {@link Item}.
     */
    void use();

}
