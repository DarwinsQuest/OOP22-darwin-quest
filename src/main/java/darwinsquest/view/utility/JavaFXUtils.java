package darwinsquest.view.utility;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Region;

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
}
