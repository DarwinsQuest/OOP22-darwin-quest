package darwinsquest.util;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Utility class for javafx.
 */
public final class JavaFXUtils {

    private JavaFXUtils() { }

    /**
     * Initialises selected background.
     * @param region the region where to set background.
     * @param imageName the image resource path to set as repeated background.
     */
    public static void initializeBackground(final Region region, final String imageName) {
        final var image = new Image(imageName);
        final var bgImg = new BackgroundImage(
            new Image(image.getUrl(), image.getWidth(), image.getHeight(), true, false),
            BackgroundRepeat.REPEAT,
            BackgroundRepeat.REPEAT,
            BackgroundPosition.CENTER,
            BackgroundSize.DEFAULT
        );
        region.setBackground(new Background(bgImg));
    }

    /**
     * Binds thinner {@link Button}s {@link Button#prefWidthProperty()} to widest {@link Button} {@link Button#widthProperty()}.
     * @param buttons the buttons to bind.
     */
    public static void bindButtonsWidthToMax(final Set<Button> buttons) {
        final var widest = buttons.stream()
            .max(Comparator.comparingInt(b -> b.getText().length()))
            .orElseThrow();

        buttons.forEach(b -> {
            if (!b.equals(widest)) {
                b.prefWidthProperty().bind(widest.widthProperty());
            }
        });
    }

    /**
     * Binds thinner {@link Button}s {@link Button#prefWidthProperty()} to widest {@link Button} {@link Button#widthProperty()}
     * that are children of the pane.
     * @param pane the pane that contains the buttons to bind.
     */
    public static void bindButtonsWidthToMax(final Pane pane) {
        bindButtonsWidthToMax(pane.getChildren().stream()
            .filter(n -> n instanceof Button)
            .map(b -> (Button) b)
            .collect(Collectors.toSet()));
    }
}
