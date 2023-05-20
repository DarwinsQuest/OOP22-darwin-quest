package darwinsquest.core;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import darwinsquest.annotation.Description;
import darwinsquest.core.difficulty.Difficulty;
import darwinsquest.core.difficulty.Normal;

/**
 * Class that represents the engine of darwinsquest model.
 */
public class EngineImpl implements Engine {

    private final List<Class<? extends Difficulty>> difficulties;
    private final Player player;
    private Optional<Board> board;
    private Optional<Difficulty> difficulty;

    /**
     * Default constructor.
     * @param player the player enveloped with the game.
     */
    public EngineImpl(final Player player) {
        difficulties = List.of(Normal.class);
        difficulty = Optional.empty();
        board = Optional.empty();
        this.player = Objects.requireNonNull(player);
    }

    private static String getDifficultyName(final Class<? extends Difficulty> difficulty) {
        return difficulty.getAnnotation(Description.class).value();
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getDifficulties() {
        return difficulties.stream().map(EngineImpl::getDifficultyName).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean startGame(final String difficulty) {
        if (this.difficulty.isPresent()) {
            return false;
        }

        difficulties.stream()
            .filter(c -> EngineImpl.getDifficultyName(c).equals(difficulty))
            .findFirst()
            .ifPresent(d -> {
                try {
                    this.difficulty = Optional.of(d.getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException
                        | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException(e);
                }
                board = Optional.of(this.difficulty.get().getBoard());
            });

        return this.difficulty.isPresent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Board> getBoard() {
        return board;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return player.isOutOfBanions();
    }
}
