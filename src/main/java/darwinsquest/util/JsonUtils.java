package darwinsquest.util;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Class that represents a general Utility for {@link com.google.gson} I/O.
 */
public final class JsonUtils {

    /**
     * Chosen charset.
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    private JsonUtils() {

    }

    /**
     * Reads a json array from a specified resource.
     * @param url the url of the json.
     * @param consumer the {@link JsonReader} consumer to read Objects.
     * @throws IOException the exception that can raise from reading a file.
     */
    public static void readJsonArrayFromResource(final String url, final Consumer<JsonReader> consumer)
            throws IOException {
        try (var reader = new JsonReader(new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(url)),
                CHARSET))) {
            reader.beginArray();
            while (reader.hasNext()) {
                consumer.accept(reader);
            }
            reader.endArray();
        }
    }

    /**
     * Reads a json array from a specified resource.
     * @param url the url of the json.
     * @param function the {@link JsonReader} function to read an Object.
     * @return a {@link Set} of the elements read.
     * @param <K> the key type of the elements to read.
     * @param <V> the value type of the elements to read.
     * @throws IOException the exception that can raise from reading a file.
     */
    public static <K, V> Map<K, V> readJsonArrayFromResourceToMap(
            final String url,
            final Function<JsonReader, Pair<K, V>> function)
                throws IOException {
        final Map<K, V> elements = new Hashtable<>();
        readJsonArrayFromResource(url, in -> {
            final var pair = function.apply(in);
            elements.put(pair.getKey(), pair.getValue());
        });
        return elements;
    }

    /**
     * Reads a json array from a specified resource.
     * @param url the url of the json.
     * @param identifier the data necessary to read a key-value pair.
     * @return a {@link Set} of the elements read.
     * @throws IOException the exception that can raise from reading a file.
     */
    public static Map<String, String> readJsonArrayFromResourceToMap(final String url, final Pair<String, String> identifier)
            throws IOException {
        return readJsonArrayFromResourceToMap(url, in -> {
            String key = "";
            String value = "";

            try {
                in.beginObject();
                while (in.hasNext()) {
                    final var id = in.nextName();
                    if (id.equals(identifier.getKey())) {
                        key = in.nextString();
                    } else if (id.equals(identifier.getValue())) {
                        value = in.nextString();
                    } else {
                        in.skipValue();
                    }
                }
                in.endObject();
            } catch (final IOException e) {
                throw new IllegalStateException(e);
            }

            return new ImmutablePair<>(key, value);
        });
    }

    /**
     * Reads a json array from a specified resource.
     * @param url the url of the json.
     * @param function the {@link JsonReader} function to read an Object.
     * @return a {@link Set} of the elements read.
     * @param <T> the type of the elements to read.
     * @throws IOException the exception that can raise from reading a file.
     */
    public static <T> Set<T> readJsonArrayFromResourceToSet(final String url, final Function<JsonReader, T> function)
            throws IOException {
        final Set<T> elements = new HashSet<>();
        readJsonArrayFromResource(url, reader -> elements.add(function.apply(reader)));
        return elements;
    }

    /**
     * Reads a json array from a specified resource.
     * @param typeClass the type of the classes to read.
     * @param gson the {@link Gson} main class.
     * @param url the url in resources.
     * @return a {@link Set} of the elements read.
     * @param <T> the type of the elements to read.
     * @throws IOException the exception that can raise from reading a file.
     */
    public static <T> Set<T> readJsonArrayFromResourceToSet(final String url, final Class<T> typeClass, final Gson gson)
            throws IOException {
        return readJsonArrayFromResourceToSet(url, reader -> gson.fromJson(reader, typeClass));
    }
}
