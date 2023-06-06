package darwinsquest.view;

import darwinsquest.BoardController;
import darwinsquest.DifficultyController;
import darwinsquest.LoginController;
import darwinsquest.SelectBanionController;

/**
 * Interface of a general view.
 */
public interface ViewManager<T extends View> {

    /**
     * Sets username in title.
     * @param username the username to set.
     */
    void setWindowTitlePrefix(String username);

    /**
     * Shows a view.
     * @param view the view to show.
     */
    void show(T view);

    // todo: remove these methods.

    /**
     * Creates a login view.
     * @param controller the MVC controller associated.
     * @return a login view.
     */
    Object createLoginView(LoginController controller);

    /**
     * Creates a banion selector view.
     * @param controller the MVC controller associated.
     * @return a selection view.
     */
    Object createBanionSelectorView(SelectBanionController controller);

    /**
     * Creates a difficulty selector view.
     * @param controller the MVC controller associated.
     * @return a difficulty view.
     */
    Object createDifficultySelectorView(DifficultyController controller);

    /**
     * Creates a board view.
     * @param controller the MVC controller associated.
     * @return a board view.
     */
    BoardView createBoardView(BoardController controller);

    /**
     * Creates a battle view.
     * @return the battle view.
     */
    Object createBattleView();

    /**
     * Creates a settings view.
     * @return the settings view.
     */
    Object createSettingsMenu();

}
