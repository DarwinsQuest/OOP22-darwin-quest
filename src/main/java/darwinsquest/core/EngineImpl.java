package darwinsquest.core;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getDifficulties() {
        return difficulties.stream().map(c -> c.getAnnotation(Description.class).value()).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean startGame(final String difficulty) {
        try {
            this.difficulty = Optional.of(difficulties.stream()
                .filter(c -> c.getAnnotation(Description.class).value().equals(difficulty))
                .findFirst()
                .get()
                .getDeclaredConstructor()
                .newInstance());
        } catch (InstantiationException | IllegalAccessException
            | InvocationTargetException | NoSuchMethodException e) {
            return false;
        }
        board = Optional.of(this.difficulty.get().getBoard());
        return true;
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
