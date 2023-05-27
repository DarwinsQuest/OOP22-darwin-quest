package darwinsquest.core.difficulty;

import com.github.javafaker.Faker;
import darwinsquest.BanionFactory;
import darwinsquest.annotation.Description;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.core.gameobject.entity.GameEntity;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import darwinsquest.core.world.BattleBoard;
import darwinsquest.core.world.BattleBoardImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents normal difficulty.
 */
@Description("Normal")
public final class Normal implements Difficulty {

    private static final int LEVELS = 10;
    private static final int MAX_STEP = 8;

    private static final int FIRST_LEVEL = 1;
    private static final int LAST_LEVEL = LEVELS + 1;

    private final AI ai;
    private final BattleBoard board;

    /**
     * Default constructor.
     */
    public Normal() {
        ai = new BasicAI();
        board = new BattleBoardImpl(LEVELS, new Die(MAX_STEP), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AI getAI() {
        return ai;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BattleBoard getBoard() {
        return board;
    }

    private Banion createFirstOpponent(final List<Banion> banions, final GameEntity player) {
        Collections.shuffle(banions);
        return banions.stream()
            .filter(b -> player.getInventory().stream()
                .map(Elemental::getElement)
                .anyMatch(e -> b.getElement().isWeaker(e)))
            .findAny()
            .orElseThrow(IllegalStateException::new);
    }

    private Collection<Banion> createMidOpponent(final List<Banion> banions, final GameEntity player) {
        Set<Banion> result;
        do {
            Collections.shuffle(banions);
            result = banions.stream()
                .limit(Math.max(MIN_OPP_BANIONS, Math.floorDiv(board.getPos() * MAX_OPP_BANIONS, LAST_LEVEL)))
                .collect(Collectors.toSet());
        } while (result.stream()
            .anyMatch(b -> player.getInventory().stream()
                .filter(Banion::isAlive)
                .map(Elemental::getElement)
                .anyMatch(e -> !b.getElement().isWeaker(e))));
        return result;
    }

    private Collection<Banion> createLastOpponent(final List<Banion> banions, final GameEntity player) {
        Collections.shuffle(banions);
        return banions.stream()
            .filter(b -> player.getInventory().stream()
                .filter(Banion::isAlive)
                .map(Elemental::getElement)
                .anyMatch(e -> b.getElement().isStronger(e)))
            .limit(Math.max(MIN_OPP_BANIONS, Math.floorDiv(board.getPos() * MAX_OPP_BANIONS, LAST_LEVEL)))
            .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameEntity createOpponent(final GameEntity player) {
        final var opponent = new OpponentImpl(new Faker().name().firstName(), getAI());
        final var banions = new ArrayList<>(new BanionFactory().createElements());
        switch (Objects.isNull(board) ? FIRST_LEVEL : board.getPos()) {
            case FIRST_LEVEL -> opponent.addToInventory(createFirstOpponent(banions, player));
            case LAST_LEVEL -> opponent.addToInventory(createLastOpponent(banions, player));
            default -> opponent.addToInventory(createMidOpponent(banions, player));
        }
        return opponent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Difficulty: Normal";
    }
}
