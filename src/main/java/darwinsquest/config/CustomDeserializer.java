package darwinsquest.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import darwinsquest.util.JsonUtils;

/**
 * Generic deserializer for custom types.
 * @param <T> the generic type to deserialize.
 */
public class CustomDeserializer<T> {

    private final Class<T> typeClass;
    private final TypeAdapterFactory factoryAdapter;
    private final String url;

    /**
     * Default constructor.
     * @param typeClass the type class of {@code T}, due to generic implementation.
     * @param classAdapter the adapter class necessary to read data,
     * this will be instantiated with the default 0 arguments constructor.
     * @param url the resource url from where to read data.
     */
    protected CustomDeserializer(final Class<T> typeClass,
            final Class<? extends TypeAdapterFactory> classAdapter,
            final String url) {
        this.url = Objects.requireNonNull(url);
        this.typeClass = Objects.requireNonNull(typeClass);
        try {
            factoryAdapter = Objects.requireNonNull(classAdapter).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Reads elements of type {@code T} read from resources.
     * @return The elements read in unmodifiable {@link Set}.
     */
    protected final Set<T> readElements() {
        try {
            return Collections.unmodifiableSet(
                JsonUtils.readJsonArrayFromResourceToSet(
                    url,
                    typeClass,
                    new GsonBuilder().registerTypeAdapterFactory(factoryAdapter).create()));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
