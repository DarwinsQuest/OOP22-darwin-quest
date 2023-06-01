package darwinsquest.config;

import java.io.IOException;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import darwinsquest.core.gameobject.move.BasicMove;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;

import static darwinsquest.generation.ElementFactory.PATH_ELEMENTS;

/**
 * An adapter to deserialize an {@link Move}.
 */
public class MoveAdapterFactory implements TypeAdapterFactory {

    private static final String NAME = "name";
    private static final String ELEMENT = "element";
    private static final String DAMAGE = "damage";

    /**
     * Default constructor.
     */
    MoveAdapterFactory() {
        // This constructor is intentionally empty because it is better package protected.
    }

    /**
    * {@inheritDoc}
    */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!Move.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) new MoveAdapter(
            new NameableLinkAdapter<Element>(Element.class, ElementAdapterFactory.class, PATH_ELEMENTS));
    }

    private static class MoveAdapter extends TypeAdapter<Move> {

        private final Element genericElement = new Neutral();

        private final TypeAdapter<Element> elementAdapter;

        protected MoveAdapter(final TypeAdapter<Element> elementAdapter) {
            this.elementAdapter = Objects.requireNonNull(elementAdapter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final JsonWriter out, final Move value) {
            throw new UnsupportedOperationException("The write operation for " + Move.class.getName() + " isn't allowed.");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Move read(final JsonReader in) throws IOException {
            int damage = 0;
            Element element = null;
            String name = null;

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case NAME: name = in.nextString(); break;
                    case ELEMENT:
                        if (in.peek() != JsonToken.NULL) {
                            element = elementAdapter.read(in);
                        } else {
                            in.nextNull();
                            element = genericElement;
                        }
                        break;
                    case DAMAGE: damage = in.nextInt(); break;
                    default: in.skipValue();
                }
            }
            in.endObject();

            return new BasicMove(damage, name, element);
        }
    }
}
