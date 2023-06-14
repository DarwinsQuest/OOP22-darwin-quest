package darwinsquest.core;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import darwinsquest.annotation.Description;
import darwinsquest.core.difficulty.Difficulty;
import darwinsquest.core.difficulty.HardDifficulty;
import darwinsquest.core.difficulty.NormalDifficulty;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.core.world.BattleBoard;
import darwinsquest.util.MyCollectors;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Class that represents the engine of darwinsquest model.
 */
public class EngineImpl implements Engine {

    private final List<Class<? extends Difficulty>> difficulties;
    private final Player player;
    private BattleBoard board;
    private Difficulty difficulty;

    /**
     * Default constructor.
     * @param player the player enveloped with the game.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Player is needed to separate concerns.")
    public EngineImpl(final Player player) {
        difficulties = List.of(NormalDifficulty.class, HardDifficulty.class);
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
        return difficulties.stream()
            .map(EngineImpl::getDifficultyName)
            .collect(MyCollectors.toImmutableSet(LinkedHashSet::new));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean startGame(final String difficulty) {
        if (Objects.nonNull(this.difficulty)) {
            return false;
        }

        difficulties.stream()
            .filter(c -> EngineImpl.getDifficultyName(c).equals(difficulty))
            .findFirst()
            .ifPresent(d -> {
                try {
                    this.difficulty = d.getDeclaredConstructor(Player.class).newInstance(player);
                } catch (InstantiationException | IllegalAccessException
                        | InvocationTargetException | NoSuchMethodException e) {
                    throw new IllegalStateException(e);
                }
                board = this.difficulty.getBoard();
            });

        return Objects.nonNull(this.difficulty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BattleBoard> getBoard() {
        return Optional.ofNullable(board);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isGameOver() {
        return player.isOutOfBanions();
    }
}
