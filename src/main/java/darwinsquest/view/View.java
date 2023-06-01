package darwinsquest.view;

import darwinsquest.BoardController;
import darwinsquest.LoginController;

/**
 * A javafx view manager.
 */
public interface View {

    /**
     * Sets username in title.
     * @param username the username to set.
     */
    void setStageTitlePrefix(String username);

    /**
     * Shows a fxml controller.
     * @param controller the controller to show.
     */
    void show(Object controller);

    /**
     * Creates a login view.
     * @param controller the MVC controller.
     * @return a login view fxml controller.
     */
    Object createLoginView(LoginController controller);

    /**
     * Creates a difficulty selector view.
     * @return a difficulty view fxml controller.
     */
    Object createDifficultySelectorView();

    /**
     * Creates a board view.
     * @param controller the MVC controller.
     * @return a board view fxml controller.
     */
    BoardView createBoardView(BoardController controller);

    /**
     * Creates a battle view.
     * @return the battle view fxml controller.
     */
    Object createBattleView();
}
