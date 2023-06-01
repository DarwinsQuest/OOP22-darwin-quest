package darwinsquest;

import java.util.Set;

/**
 * Interface that represents this project main controller.
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
     * Starts a game.
     */
    void startController();

    /**
     * Retrieves the ordered {@link Set} of difficulties.
     * @return the ordered {@link Set} of difficulties
     */
    Set<String> getDifficulties();

    /**
     * Starts a new game with provided difficulty.
     * @param difficulty a difficulty provided by {@link Controller#getDifficulties()}.
     */
    void startGame(String difficulty);

//    /**
//     * Starts the battle at the board corresponding level.
//     * @return if the player won.
//     */
//    boolean startBattle();
}
