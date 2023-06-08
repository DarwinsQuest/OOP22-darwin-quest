package darwinsquest.view;

/**
 * A board view.
 */
public interface BoardView {

    /**
     * Initializes the view with useful data.
     * @param pos the initial position int the board.
     * @param levels the total number of levels.
     * @param maxStep the max step of movement.
     * @param canMove a flag that communicates if player can move in the map.
     * @param canFight a flag that communicates if player can fight.
     */
    void init(int pos, int levels, int maxStep, boolean canMove, boolean canFight);

    /**
     * Sets the actual position.
     * @param pos the actual position.
     */
    void setPos(int pos);

    /**
     * Sets the actual possibility to move.
     * @param canMove the actual possibility to move.
     */
    void toggleMove(boolean canMove);

    /**
     * Sets the actual possibility to fight.
     * @param canFight the actual possibility to fight.
     */
    void toggleFight(boolean canFight);
}
