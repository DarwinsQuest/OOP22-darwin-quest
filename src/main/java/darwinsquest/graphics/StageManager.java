package darwinsquest.graphics;

/**
 * A javafx {@link javafx.stage.Stage} manager.
 */
public interface StageManager {

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
