package darwinsquest.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import darwinsquest.core.Nameable;

/**
 * An adapter to deserialize a {@link Namable}.
 * @param <T> type that represents the {@link Nameable} instance.
 */
class NamableLinkAdapter<T extends Nameable> extends TypeAdapter<T> {

    private final String path;
    private final Class<T> typeClass;
    private final TypeAdapterFactory typeFactory;
    private Set<T> elements;

    NamableLinkAdapter(final Class<T> typeClass, final Class<? extends TypeAdapterFactory> classAdapter, final String path) {
        this.path = Objects.requireNonNull(path);
        this.typeClass = Objects.requireNonNull(typeClass);
        try {
            typeFactory = Objects.requireNonNull(classAdapter).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private void readElements() throws IOException {
        elements = new HashSet<>();
        final var builder = new GsonBuilder();
        final var gson = builder.registerTypeAdapterFactory(typeFactory).create();
        try (var reader = new JsonReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(path), StandardCharsets.UTF_8))) {
            reader.beginArray();
            while (reader.hasNext()) {
                elements.add(gson.<T>fromJson(reader, typeClass));
            }
            reader.endArray();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final JsonWriter out, final T value) throws IOException {
        out.value(value.getName());
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
        final var result = elements.stream().filter(e -> e.getName().equals(name)).findFirst();
        if (result.isEmpty()) {
            throw new IllegalStateException();
        }
        return result.get();
    }
}
