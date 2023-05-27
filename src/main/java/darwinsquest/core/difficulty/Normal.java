package darwinsquest.core.difficulty;

import com.github.javafaker.Faker;
import darwinsquest.BanionFactory;
import darwinsquest.annotation.Description;
import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.element.Elemental;
import darwinsquest.core.gameobject.entity.Opponent;
import darwinsquest.core.gameobject.entity.OpponentImpl;
import darwinsquest.core.gameobject.entity.Player;
import darwinsquest.core.world.Board;
import darwinsquest.core.world.BoardImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
    private final Board board;

    /**
     * Default constructor.
     */
    public Normal() {
        ai = new BasicAI();
        board = new BoardImpl(LEVELS, new Die(MAX_STEP));
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
    public Board getBoard() {
        return board;
    }

    private Banion getFirstOpponent(final Player player, final List<Banion> banions) {
        Collections.shuffle(banions);
        return banions.stream()
            .filter(b -> player.getInventory().stream()
                .map(Elemental::getElement)
                .anyMatch(e -> b.getElement().isWeaker(e)))
            .findAny()
            .orElseThrow(IllegalStateException::new);
    }

    private Collection<Banion> getMidOpponent(final Player player, final List<Banion> banions) {
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

    private Collection<Banion> getLastOpponent(final Player player, final List<Banion> banions) {
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
    public Opponent getOpponent(final Player player) {
        final var opponent = new OpponentImpl(new Faker().name().firstName(), getAI());
        final var banions = new ArrayList<>(new BanionFactory().createElements());
        switch (board.getPos()) {
            case FIRST_LEVEL -> opponent.addToInventory(getFirstOpponent(player, banions));
            case LAST_LEVEL -> opponent.addToInventory(getLastOpponent(player, banions));
            default -> opponent.addToInventory(getMidOpponent(player, banions));
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
