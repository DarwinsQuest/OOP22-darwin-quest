package darwinsquest.view;

/**
 * A javafx {@link javafx.stage.Stage} manager.
 */
public interface StageManager {

    /**
     * Sets username in title.
     * @param username the username to set.
     */
    void setUsername(String username);

    /**
     * Shows start menu {@link javafx.scene.Scene}.
     */
    void showStartMenu();

    /**
     * Shows login {@link javafx.scene.Scene}.
     */
    void showLogin();

    /**
     * Shows difficulty selector {@link javafx.scene.Scene}.
     */
    void showDifficulties();

    /**
     * Shows battle {@link javafx.scene.Scene}.
     */
    void showBattle();
}
