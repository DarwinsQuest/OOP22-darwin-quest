package darwinsquest.view;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

/**
 * Class that represent a basic view.
 */
public class BasicView {

    private final View view;

    /**
     * Default constructor.
     * @param view the MVC view.
     */
    protected BasicView(final View view) {
        this.view = Objects.requireNonNull(view);
    }

    /**
     * Retrieves current view.
     * @return the current view.
     */
    protected View getView() {
        return view;
    }

    @FXML
    private void onEscPressed(final KeyEvent event) { // NOPMD, events are indirectly used
        if (event.getCode().equals(KeyCode.ESCAPE)) {
            view.show(new SettingsView(view, this));
        }
    }
}
