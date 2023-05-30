package darwinsquest.config;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import darwinsquest.view.graphics.Sprite;

import java.io.IOException;

/**
 * An adapter to deserialize a {@link darwinsquest.view.graphics.Sprite}.
 */
public class SpriteAdapterFactory implements TypeAdapterFactory {

    private static final String URL = "src";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String FRAMES = "frames";

    /**
     * {@link darwinsquest.core.gameobject.banion.Banion}'s Idle sprite url.
     */
    public static final String IDLE = "idle";
    /**
     * {@link darwinsquest.core.gameobject.banion.Banion}'s Hit sprite url.
     */
    public static final String HIT = "hit";

    /**
     * Default constructor.
     */
    SpriteAdapterFactory() {
        // This constructor is intentionally empty because it is better package protected.
    }

    /**
    * {@inheritDoc}
    */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!Sprite.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) new SpriteAdapter();
    }

    private static class SpriteAdapter extends TypeAdapter<Sprite> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final JsonWriter out, final Sprite value) {
            throw new UnsupportedOperationException("The write operation for " + Sprite.class.getName() + " isn't allowed.");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Sprite read(final JsonReader in) throws IOException {
            String url = null;
            int width = 0;
            int height = 0;
            int frames = 0;

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case URL: url = in.nextString(); break;
                    case WIDTH: width = in.nextInt(); break;
                    case HEIGHT: height = in.nextInt(); break;
                    case FRAMES: frames = in.nextInt(); break;
                    default: in.skipValue();
                }
            }
            in.endObject();

            return new Sprite(url, width, height, frames);
        }
    }
}
