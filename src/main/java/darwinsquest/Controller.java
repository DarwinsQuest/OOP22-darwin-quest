package darwinsquest;

import java.util.Set;

/**
 * Interface that represents this project controller.
 */
public interface Controller {

    /**
     * Path to {@link darwinsquest.core.gameobject.element.Element} definitions.
     */
    String PATH_ELEMENTS = "config/elements.json";
    /**
     * Path to {@link darwinsquest.core.gameobject.move.Move} definitions.
     */
    String PATH_MOVES = "config/moves.json";
    /**
     * Path to {@link darwinsquest.core.gameobject.banion.Banion} definitions.
     */
    String PATH_BANIONS = "config/banions.json";

    /**
     * Checks if {@code username} is valid for {@link Controller#login(String)} action.
     * @param username the user identifier.
     * @return if {@code username} is valid.
     */
    boolean isValidUsername(String username);

    /**
     * Registers {@code username} to actual game.
     * @param username the user identifier.
     */
    void login(String username);

    /**
     * Retrieves the ordered {@link Set} of difficulties.
     * @return the ordered {@link Set} of difficulties
     */
    Set<String> getDifficulties();

    /**
     * Starts a new game with provided difficulty.
     * @param difficulty a difficulty provided by {@link Controller#getDifficulties()}.
     * @return if {@code difficulty} is valid.
     */
    boolean startGame(String difficulty);
}
