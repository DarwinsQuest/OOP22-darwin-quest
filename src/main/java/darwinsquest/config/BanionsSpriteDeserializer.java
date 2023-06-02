package darwinsquest.config;

import com.google.gson.GsonBuilder;
import darwinsquest.util.JsonUtils;
import darwinsquest.view.graphics.Sprite;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static darwinsquest.generation.BanionFactory.PATH_BANIONS;

/**
 * Class that reads {@link darwinsquest.view.graphics.Sprite} data from file.
 */
public final class BanionsSpriteDeserializer {

    private BanionsSpriteDeserializer() {

    }

    /**
     * Reads {@link Sprite} data from resource.
     * @return map with {@link darwinsquest.core.gameobject.banion.Banion}
     * as key, and a pair of IDLE and HIT sprites as value.
     */
    public static Map<String, Pair<Sprite, Sprite>> read() {
        try {
            return JsonUtils.readJsonArrayFromResourceToMap(PATH_BANIONS, in -> {
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
}
