package darwinsquest.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.ImmutableElement;

/**
 * An adapter to deserialize an {@link Element}.
 */
public class ElementAdapterFactory implements TypeAdapterFactory {

    private static final String NAME = "name";
    private static final String WEAKER = "weaker";
    private static final String STRONGER = "stronger";

    /**
     * Default constructor.
     */
    ElementAdapterFactory() {
        // This constructor is intentionally empty because it is better package protected.
    }

    /**
    * {@inheritDoc}
    */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!Element.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) new ElementAdapter();
    }

    private static class ElementAdapter extends TypeAdapter<Element> {

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final JsonWriter out, final Element value) {
            throw new UnsupportedOperationException("The write operation for " + Element.class.getName() + " isn't allowed.");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Element read(final JsonReader in) throws IOException {
            String name = null;
            final Set<String> weaker = new HashSet<>();
            final Set<String> stronger = new HashSet<>();

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case NAME: name = in.nextString(); break;
                    case WEAKER: in.beginArray();
                        while (in.hasNext()) {
                            weaker.add(in.nextString());
                        }
                        in.endArray();
                        break;
                    case STRONGER: in.beginArray();
                        while (in.hasNext()) {
                            stronger.add(in.nextString());
                        }
                        in.endArray();
                        break;
                    default: in.skipValue();
                }
            }
            in.endObject();

            return new ImmutableElement(name, weaker, stronger);
        }
    }
}
