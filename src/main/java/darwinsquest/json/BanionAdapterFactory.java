package darwinsquest.json;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import darwinsquest.ControllerImpl;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.move.Move;
import darwinsquest.core.gameobject.element.Element;

/**
 * An adapter to deserialize an {@link Banion}.
 */
public class BanionAdapterFactory implements TypeAdapterFactory {

    /**
     * Default constructor.
     */
    BanionAdapterFactory() {
        // This constructor is intentionally empty because it is better package protected.
    }

    /**
    * {@inheritDoc}
    */
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!Banion.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) new BanionAdapter(
            new NameableLinkAdapter<Element>(Element.class, ElementAdapterFactory.class, ControllerImpl.PATH_ELEMENTS),
            new NameableLinkAdapter<Move>(Move.class, MoveAdapterFactory.class, ControllerImpl.PATH_MOVES));
    }

    private static class BanionAdapter extends TypeAdapter<Banion> {

        private static final String NAME = "name";
        private static final String ELEMENT = "element";
        private static final String HP = "hp";
        private static final String MOVES = "moves";

        private final TypeAdapter<Element> elementAdapter;
        private final TypeAdapter<Move> moveAdapter;

        protected BanionAdapter(final TypeAdapter<Element> elementAdapter, final TypeAdapter<Move> moveAdapter) {
            this.elementAdapter = Objects.requireNonNull(elementAdapter);
            this.moveAdapter = Objects.requireNonNull(moveAdapter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final JsonWriter out, final Banion value) throws IOException {
            out.beginObject();
            out.name(ELEMENT);
            elementAdapter.write(out, value.getElement());
            out.name(NAME).value(value.getName());
            out.name(HP).value(value.getHp());
            out.name(MOVES);
            out.beginArray();
            for (final var m: value.getMoves()) {
                moveAdapter.write(out, m);
            }
            out.endArray();
            out.endObject();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public Banion read(final JsonReader in) throws IOException {
            int hp = 0;
            final Set<Move> moves = new HashSet<>();
            Element element = null;
            String name = null;

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case NAME: name = in.nextString(); break;
                    case ELEMENT: element = elementAdapter.read(in); break;
                    case HP: hp = in.nextInt(); break;
                    case MOVES:
                        in.beginArray();
                        while (in.hasNext()) {
                            moves.add(moveAdapter.read(in));
                        }
                        in.endArray();
                        break;
                    default: in.skipValue();
                }
            }
            in.endObject();

            return new BanionImpl(element, name, hp, moves);
        }
    }
}
