package darwinsquest.json;

import com.google.gson.GsonBuilder;
import darwinsquest.Controller;
import darwinsquest.json.utility.JsonUtils;
import darwinsquest.view.graphics.Sprite;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Class that maps a banion name with the specified url location of its sprite.
 */
public final class BanionsSpriteDeserializer {

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

    private final Map<String, Pair<Sprite, Sprite>> map;

    /**
     * Default constructor.
     */
    public BanionsSpriteDeserializer() {
        try {
            map = JsonUtils.readJsonArrayFromResourceToMap(Controller.PATH_BANIONS, in -> {
                String key = null;
                Sprite idle = null;
                Sprite hit = null;
                final var gson = new GsonBuilder().registerTypeAdapterFactory(new SpriteAdapterFactory()).create();

                try {
                    in.beginObject();
                    while (in.hasNext()) {
                        switch (in.nextName()) {
                            case BanionAdapterFactory.NAME: key = in.nextString(); break;
                            case BanionAdapterFactory.SPRITES:
                                    in.beginObject();
                                    while (in.hasNext()) {
                                        switch (in.nextName()) {
                                            case SpriteAdapterFactory.IDLE: idle = gson.fromJson(in, Sprite.class); break;
                                            case SpriteAdapterFactory.HIT: hit = gson.fromJson(in, Sprite.class); break;
                                            default: in.skipValue();
                                        }
                                    }
                                    in.endObject();
                                break;
                            default: in.skipValue();
                        }
                    }
                    in.endObject();
                } catch (final IOException e) {
                    throw new IllegalStateException(e);
                }

                return new ImmutablePair<>(key,
                    new ImmutablePair<>(Objects.requireNonNull(idle), Objects.requireNonNull(hit)));
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
    public Sprite getBanionSprite(final String name, final SpriteType type) {
        return type.getSpriteByType(map.get(name));
    }
}
