package darwinsquest.view;

import darwinsquest.Controller;
import darwinsquest.core.world.Board;
import darwinsquest.util.JavaFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that represents the fxml view controller of the start menu.
 */
public final class BoardController extends ControllerStageInteractive implements Initializable {

    private static final String PREFIX = "Level: ";
    private final String suffix;

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

    private final Board board;

    /**
     * Default constructor.
     * @param manager the stage manager related to this controller.
     * @param controller the MVC controller.
     */
    public BoardController(final StageManager manager, final Controller controller) {
        super(manager, controller);
        this.board = getController().getBoard();
        suffix = " (max step = " + board.getMaxStep() + ")";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(final URL location, final ResourceBundle resources) {
        setLabelText(Integer.toString(board.getPos()));
        progress.setProgress((double) board.getPos() / board.getLastPos());
        btMove.setDisable(!board.canMove());
        btFight.setDisable(board.canMove());
        JavaFXUtils.initializeBackground(vBox, "img/Blue.png");
    }

    private void setLabelText(final String text) {
        lbLevel.setText(PREFIX + text + suffix);
    }

    @FXML
    private void onMoveAction() { // NOPMD, events are indirectly used
        setLabelText(Integer.toString(board.getPos() + board.move().orElseThrow()));
        progress.setProgress((double) board.getPos() / board.getLastPos());
    }

    @FXML
    private void onFightAction() { // NOPMD, events are indirectly used
        getManager().showBattle();
    }
}
