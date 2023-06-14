package darwinsquest.controller;

import java.util.List;

/**
 * Interface that represents a banions selector controller.
 */
public interface SelectBanionController {

    /**
     * Retrieves the Banions that can be chosen.
     * @return the Banions that can be chosen.
     */
    List<BanionController> getBanions();

    /**
     * Checks if a banion is yet selected.
     * @param banion the banion to check.
     * @return if selected.
     */
    boolean isSelected(BanionController banion);

    /**
     * Checks if another Banion can be selected.
     * @return if another Banion can be selected.
     */
    boolean canSelect();

    /**
     * Adds a Banion to the selected.
     * @param banion the banion to add.
     * @return if the Banion was added correctly.
     */
    boolean selectBanion(BanionController banion);

    /**
     * Removes a Banion from the selected.
     * @param banion the banion to remove.
     * @return if the Banion was removed correctly.
     */
    boolean deselectBanion(BanionController banion);

    /**
     * Tells if the selection can be confirmed.
     * @return if the selection can be confirmed.
     */
    boolean canConfirm();

    /**
     * Confirms the Banions selected.
     * @return if confirmation was legal.
     */
    boolean confirm();
}
