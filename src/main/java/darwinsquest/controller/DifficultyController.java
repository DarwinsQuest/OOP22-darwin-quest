package darwinsquest.controller;

import java.util.Set;

/**
 * Interface that represents a difficulty controller.
 */
public interface DifficultyController {

    /**
     * Retrieves the ordered {@link Set} of difficulties.
     * @return the ordered {@link Set} of difficulties
     */
    Set<String> getDifficulties();

    /**
     * Starts a new game with provided difficulty.
     * @param difficulty a difficulty provided by {@link DifficultyController#getDifficulties()}.
     * @return if difficulty correctly selected.
     */
    boolean selectDifficulty(String difficulty);
}
