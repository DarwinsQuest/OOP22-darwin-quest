package darwinsquest.controller;

/**
 * Interface that represents a login controller.
 */
public interface LoginController {

    /**
     * Checks if {@code username} is valid for {@link LoginController#login(String)} action.
     * @param username the user identifier.
     * @return if {@code username} is valid.
     */
    boolean isValidUsername(String username);

    /**
     * Registers {@code username} to actual game.
     * @param username the user identifier.
     */
    void login(String username);
}
