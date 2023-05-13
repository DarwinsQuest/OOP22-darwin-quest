package darwinsquest.core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

/**
 * Class that represents the engine of darwinsquest model.
 */
public class EngineImpl implements Engine {

    private final List<Class<? extends Difficulty>> difficulties;

    /**
     * Costructor that creates a {@link EngineImpl}.
     */
    public EngineImpl() {
        difficulties = List.of(Normal.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDifficulties() {
        return difficulties.stream().map(Class::getName).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> startGame(final String difficulty) {
        try {
            return Optional.ofNullable(difficulties.stream()
                .map(Class::getName)
                .noneMatch(difficulty::equals)
                    ? null
                    : difficulties.stream()
                        .filter(c -> c.getName().equals(difficulty))
                        .findFirst()
                        .get()
                        .getDeclaredConstructor()
                        .newInstance()
                        .getBoard()
                );
        } catch (InstantiationException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException
            | NoSuchMethodException | SecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        throw new UnsupportedOperationException("Unimplemented method 'isGameOver'");
    }
}
