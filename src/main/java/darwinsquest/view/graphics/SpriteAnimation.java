package darwinsquest.view.graphics;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Class that represents a sprite animation.
 */
public class SpriteAnimation extends Transition {

    private static final int SPRITE_UPSCALE = 4;
    private final ImageView imageView;
    private final int frames;
    private final int columns;
    private final int width;
    private final int height;
    private final boolean horizontalFlip;
    private final Image spriteSheet;
    private int lastIndex;

    /**
     * The sprite animation constructor.
     * @param imageView the {@code Node} that contains the sprite.
     * @param spriteSheet the sprite sheet.
     * @param duration the duration of the animation.
     * @param count the amount of times the animation will be played.
     * @param frames the number of frames present in the sprite sheet.
     * @param columns the number of columns in the sprite sheet.
     * @param width the width of a single frame.
     * @param height the height of a single frame.
     * @param horizontalFlip when set to {@code true} the sprite will be horizontally flipped.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing mutable objects is needed to display animations.")
    public SpriteAnimation(
            final ImageView imageView,
            final Image spriteSheet,
            final Duration duration, final int count,
            final int frames, final int columns,
            final int width, final int height,
            final boolean horizontalFlip
    ) {
        this.imageView = imageView;
        this.spriteSheet = spriteSheet;
        this.frames = frames;
        this.columns = columns;
        this.width = width * SPRITE_UPSCALE;
        this.height = height * SPRITE_UPSCALE;
        this.horizontalFlip = horizontalFlip;
        setCycleDuration(duration);
        setCycleCount(count);
        setInterpolator(Interpolator.LINEAR);
        setSprite();
    }

    /**
     * The sprite animation constructor.
     * @param imageView the {@code Node} that contains the sprite.
     * @param sprite the sprite.
     * @param horizontalFlip when set to {@code true} the sprite will be horizontally flipped.
     * @param duration the duration of the animation.
     * @param count the amount of times the animation will be played.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Storing mutable objects is needed to display animations.")
    public SpriteAnimation(
        final ImageView imageView,
        final Sprite sprite,
        final Duration duration,
        final int count,
        final boolean horizontalFlip
    ) {
        this.imageView = imageView;
        this.spriteSheet = sprite.getImage();
        this.frames = sprite.frames();
        this.columns = sprite.frames();
        this.width = sprite.width() * SPRITE_UPSCALE;
        this.height = sprite.height() * SPRITE_UPSCALE;
        this.horizontalFlip = horizontalFlip;
        setCycleDuration(duration);
        setCycleCount(count);
        setInterpolator(Interpolator.LINEAR);
        setSprite();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void interpolate(final double k) {
        final int index = Math.min((int) Math.floor(k * frames), frames - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width;
            final int y = (index / columns) * height;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            if (horizontalFlip) {
                imageView.setScaleX(-1);
            }
            lastIndex = index;
        }
    }

    private void setSprite() {
        imageView.setImage(new Image(
                spriteSheet.getUrl(),
                spriteSheet.getWidth() * SPRITE_UPSCALE,
                spriteSheet.getHeight() * SPRITE_UPSCALE,
                true,
                false)
        );
    }

}
