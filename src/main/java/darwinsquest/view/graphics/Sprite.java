package darwinsquest.view.graphics;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Record that represents a Banion sprite.
 * @param url the url.
 * @param width the width.
 * @param height the height.
 * @param frames the number of frames and columns in the sprite sheet.
 */
public record Sprite(String url, int width, int height, int frames) {

    /**
     * Default constructor.
     * @param url the url.
     * @param width the width.
     * @param height the height.
     * @param frames the number of frames and columns in the sprite sheet.
     */
    public Sprite {
        Objects.requireNonNull(url);
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(Sprite.class.getName() + " dimensions can't be negative or zero.");
        }
        if (frames <= 0) {
            throw new IllegalArgumentException(Sprite.class.getName() + " frames can't be negative or zero.");
        }
    }

    /**
     * Returns the {@link Image} at the specified url.
     * @return the {@link Image}.
     */
    public Image getImage() {
        return new Image(url);
    }
}
