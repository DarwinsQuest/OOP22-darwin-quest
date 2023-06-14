package darwinsquest.view;

import darwinsquest.controller.BoardController;
import darwinsquest.annotation.Description;
import darwinsquest.util.JavaFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the start menu.
 */
@Description("board")
public final class BoardViewImpl extends ControllerInteractive<BoardController> implements Initializable, BoardView {

    private static final String PREFIX = "Level: ";
    @FXML
    private VBox vBox;
    @FXML
    private Label lbLevel;
    @FXML
    private Button btMove;
    @FXML
    private Button btFight;
    @FXML
    private ProgressBar progress;

    private int levels;
    private int pos;
    private boolean canMove;
    private boolean canFight;

    /**
     * Default constructor.
     * @param view the MVC view.
     * @param controller the MVC controller.
     */
    public BoardViewImpl(final View view, final BoardController controller) {
        super(view, controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(final int pos, final int levels, final int maxStep, final boolean canMove, final boolean canFight) {
        this.levels = levels;
        this.pos = pos;
        this.canMove = canMove;
        this.canFight = canFight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
        setPos(pos);
        toggleMove(canMove);
        toggleFight(canFight);
        JavaFXUtils.bindButtonsWidthToMax(vBox);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPos(final int pos) {
        this.pos = pos;
        setLabelText(Integer.toString(pos));
        progress.setProgress((double) pos / levels);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleMove(final boolean canMove) {
        this.canMove = canMove;
        btMove.setDisable(!canMove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void toggleFight(final boolean canFight) {
        this.canFight = canFight;
        btFight.setDisable(!canFight);
    }

    private void setLabelText(final String text) {
        if (this.pos == getController().getLastPos()) {
            lbLevel.setText("!!! FINAL BOSS !!!");
            lbLevel.setTextFill(Color.CRIMSON);
            JavaFXUtils.initializeBackground(vBox, "img/Purple.png");
        } else {
            lbLevel.setText(PREFIX + text);
        }
    }

    @FXML
    private void onMoveAction() { // NOPMD, events are indirectly used
        getController().move();
    }

    @FXML
    private void onFightAction() { // NOPMD, events are indirectly used
        getController().startBattle();
    }
}
