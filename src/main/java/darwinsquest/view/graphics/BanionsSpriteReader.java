package darwinsquest.view.graphics;

import darwinsquest.Controller;
import darwinsquest.json.BanionAdapterFactory;
import darwinsquest.json.utility.JsonUtils;
import javafx.scene.image.Image;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

/**
 * Class that maps a banion name with the specified url location of its sprite.
 */
public final class BanionsSpriteReader {

    /**
     * Manages the type of the sprite.
     */
    public enum Sprite {
        /**
         * Idle sprite.
         */
        IDLE(Pair::getLeft),
        /**
         * Hit sprite.
         */
        HIT(Pair::getRight);

        private final Function<Pair<String, String>, String> selector;

        Sprite(final Function<Pair<String, String>, String> spriteSelector) {
            selector = spriteSelector;
        }

        String getSpriteUrl(final Pair<String, String> urls) {
            return selector.apply(urls);
        }
    }

    private final Map<String, Pair<String, String>> map;

    /**
     * Default constructor.
     */
    public BanionsSpriteReader() {
        try {
            map = JsonUtils.readJsonArrayFromResourceToMap(Controller.PATH_BANIONS, in -> {
                String key = "";
                String valueIdle = "";
                String valueHit = "";

                try {
                    in.beginObject();
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case BanionAdapterFactory.NAME: key = in.nextString(); break;
                            case BanionAdapterFactory.IDLE: valueIdle = in.nextString(); break;
                            case BanionAdapterFactory.HIT: valueHit = in.nextString(); break;
                            default: in.skipValue();
                        }
                    }
                    in.endObject();
                } catch (final IOException e) {
                    throw new IllegalStateException(e);
                }

                return new ImmutablePair<>(key, new ImmutablePair<>(valueIdle, valueHit));
            });
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves a banion sprite.
     * @param name the name of the banion.
     * @param type the type of the image.
     * @return the sprite of the banion.
     */
    public Image getBanionSprite(final String name, final Sprite type) {
        return new Image(type.getSpriteUrl(map.get(name)));
    }
}
