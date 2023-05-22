package darwinsquest.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;

/**
 * Generic deserializer for custom types.
 * @param <T> the generic type to deserialize.
 */
public class CustomDeserializer<T> {

    private final Class<T> typeClass;
    private final TypeAdapterFactory factoryAdapter;
    private final String path;

    private String errorMessage;

    /**
     * Default constructor.
     * @param typeClass the type class of {@code T}, due to generic implementation.
     * @param classAdapter the adapter class necessary to read data,
     * this will be instantiated with the default 0 arguments constructor.
     * @param path the path where to read data.
     */
    protected CustomDeserializer(final Class<T> typeClass,
            final Class<? extends TypeAdapterFactory> classAdapter,
            final String path) {
        this.path = Objects.requireNonNull(path);
        this.typeClass = Objects.requireNonNull(typeClass);
        try {
            factoryAdapter = Objects.requireNonNull(classAdapter).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    private void cleanMessage() {
        errorMessage = null;
    }

    /**
     * Reads elements of type {@code T} read from file.
     * @return The elements read in unmodifiable {@link Set}, {@link Optional#empty()} if error occurred.
     */
    protected final Optional<Set<T>> readElements() {
        cleanMessage();
        final Set<T> elements = new HashSet<>();
        final var gson = new GsonBuilder().registerTypeAdapterFactory(factoryAdapter).create();
        try {
            try (var reader = new JsonReader(
                    new InputStreamReader(ClassLoader.getSystemResourceAsStream(path), StandardCharsets.UTF_8))) {
                reader.beginArray();
                while (reader.hasNext()) {
                    elements.add(gson.<T>fromJson(reader, typeClass));
                }
                reader.endArray();
            }
        } catch (final IOException e) {
            errorMessage = Objects.isNull(e.getLocalizedMessage())
                ? "Generic input error while reading from " + ClassLoader.getSystemResource(path)
                : e.getLocalizedMessage();
            return Optional.empty();
        }
        return Optional.of(Collections.unmodifiableSet(elements));
    }

    /**
     * Retrieves the error message.
     * @return the error message if something gone wrong,
     * {@link #readElements()} returned {@link Optional#empty()}.
     */
    public final Optional<String> getError() {
        return Optional.ofNullable(errorMessage);
    }
}
