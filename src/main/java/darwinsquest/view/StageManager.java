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

    /**
     * Shows the inventory menu during the battle.
     */
    void showInventoryMenu();

    /**
     * Shows the menu that allows the {@link darwinsquest.core.gameobject.entity.Player} to choose
     * their {@link darwinsquest.core.gameobject.banion.Banion}s at the beginning of the game.
     */
    void showChooseBanionMenu();
}
