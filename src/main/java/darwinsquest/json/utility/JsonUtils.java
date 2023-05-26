package darwinsquest.json.utility;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a general Utility for {@link com.google.gson} I/O.
 */
public final class JsonUtils {

    private JsonUtils() {

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
    public static <T> Set<T> readJsonArrayFromResource(final Class<T> typeClass, final Gson gson, final String url)
            throws IOException {
        final Set<T> elements = new HashSet<>();
        try (var reader = new JsonReader(new InputStreamReader(
                Objects.requireNonNull(ClassLoader.getSystemResourceAsStream(url)),
                StandardCharsets.UTF_8))) {
            reader.beginArray();
            while (reader.hasNext()) {
                elements.add(gson.fromJson(reader, typeClass));
            }
            reader.endArray();
        }
        return elements;
    }
}
