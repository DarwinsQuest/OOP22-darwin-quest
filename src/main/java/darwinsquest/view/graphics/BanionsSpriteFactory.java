package darwinsquest.view.graphics;

import darwinsquest.config.BanionsSpriteDeserializer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.function.Function;

/**
 * Class that maps a banion name with the specified url location of its sprite.
 */
public final class BanionsSpriteFactory {

    /**
     * Manages the type of the sprite.
     */
    public enum SpriteType {
        /**
         * Idle sprite.
         */
        IDLE(Pair::getLeft),
        /**
         * Hit sprite.
         */
        HIT(Pair::getRight);

        private final Function<Pair<Sprite, Sprite>, Sprite> selector;

        SpriteType(final Function<Pair<Sprite, Sprite>, Sprite> selector) {
            this.selector = selector;
        }

        Sprite getSpriteByType(final Pair<Sprite, Sprite> sprites) {
            return selector.apply(sprites);
        }
    }

    private final Map<String, Pair<Sprite, Sprite>> cache;

    /**
     * Default constructor.
     */
    public BanionsSpriteFactory() {
        cache = BanionsSpriteDeserializer.read();
    }

    /**
     * Retrieves a banion sprite.
     * @param name the name of the banion.
     * @param type the type of the image.
     * @return the sprite of the banion.
     */
    public Sprite getBanionSprite(final String name, final SpriteType type) {
        return type.getSpriteByType(cache.get(name));
    }
}
