package darwinsquest.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import darwinsquest.core.gameobject.Nameable;
import darwinsquest.util.JsonUtils;

/**
 * An adapter to deserialize a {@link Nameable}.
 * @param <T> type that represents the {@link Nameable} instance.
 */
class NameableLinkAdapter<T extends Nameable> extends TypeAdapter<T> {

    private final String url;
    private final Class<T> typeClass;
    private final TypeAdapterFactory typeFactory;
    private Set<T> elements;

    /**
     * Default constructor.
     * @param typeClass the type of the {@link darwinsquest.core.gameobject.Nameable} class.
     * @param classAdapter the class adapter.
     * @param url the resource file url.
     */
    NameableLinkAdapter(final Class<T> typeClass, final Class<? extends TypeAdapterFactory> classAdapter, final String url) {
        this.url = Objects.requireNonNull(url);
        this.typeClass = Objects.requireNonNull(typeClass);
        try {
            typeFactory = Objects.requireNonNull(classAdapter).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private void readElements() throws IOException {
        elements = JsonUtils.readJsonArrayFromResourceToSet(url,
            typeClass,
            new GsonBuilder().registerTypeAdapterFactory(typeFactory).create());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final JsonWriter out, final T value) {
        throw new UnsupportedOperationException("The write operation for " + typeClass.getName() + " isn't allowed.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T read(final JsonReader in) throws IOException {
        if (Objects.isNull(elements)) {
            readElements();
        }
        final String name = in.nextString();
        return elements.stream()
            .filter(e -> e.getName().equals(name))
            .findFirst()
            .orElseThrow();
    }
}
