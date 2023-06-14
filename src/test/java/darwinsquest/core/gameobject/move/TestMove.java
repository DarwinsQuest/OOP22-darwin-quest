package darwinsquest.core.gameobject.move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import darwinsquest.generation.BanionFactory;
import darwinsquest.core.gameobject.banion.Banion;
import org.junit.jupiter.api.Test;
import darwinsquest.generation.MoveFactory;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestMove {

    private static final int MIN_DAMAGE_INFLICTED = 1;
    private static final int ILLEGAL_BASE_DAMAGE = -10;
    private static final int LEGAL_BASE_DAMAGE_1 = 20;
    private static final int LEGAL_BASE_DAMAGE_2 = 10;
    private static final String MOVE_NAME_1 = "Fireball";
    private static final String MOVE_NAME_2 = "Splash";
    private final Element neutral = new Neutral();
    private static final Set<Banion> ALL_BANIONS = new BanionFactory().createElements();

    @Test
    void testMoveCreation() {
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(ILLEGAL_BASE_DAMAGE, MOVE_NAME_1, neutral));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, null, neutral));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, "", neutral));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, "  ", neutral));
        assertThrows(NullPointerException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, null));

        final DamageMove move = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, neutral);
        assertEquals(LEGAL_BASE_DAMAGE_1, move.getBaseDamage());
        assertEquals(neutral, move.getElement());
        assertEquals(MOVE_NAME_1, move.getName());
    }

    @Test
    void testComputeDamage() {
        final var banions = new ArrayList<>(ALL_BANIONS);
        Collections.shuffle(banions);
        final var chosenBanion = banions.stream().findFirst().get();
        final Set<DamageMove> chosenBanionMoves = new HashSet<>();
        for (final var move : chosenBanion.getMoves()) {
            chosenBanionMoves.add((DamageMove) move);
        }
        banions.remove(chosenBanion);
        for (final var banion : banions) {
            for (final var move : chosenBanionMoves) {
                assertEquals(move.computeDamage(chosenBanion, banion), computeCorrectDamage(move, chosenBanion, banion));
                assertTrue(move.computeDamage(chosenBanion, banion) >= MIN_DAMAGE_INFLICTED);
            }
        }
    }

    @Test
    void testPerformMove() {
        final var banions = new ArrayList<>(ALL_BANIONS);
        Collections.shuffle(banions);
        final var chosenBanion = banions.stream().findAny().get();
        banions.remove(chosenBanion);
        final var chosenMove = (DamageMove) chosenBanion.getMoves().stream().findAny().get();
        for (final var banion : banions) {
            chosenMove.perform(chosenBanion, banion);
            assertEquals(chosenMove.computeDamage(chosenBanion, banion), banion.getMaxHp() - banion.getHp());
        }
    }

    @Test
    void testEquality() {
        final var element = new MoveFactory().createElements().stream()
            .filter(m -> !m.getElement().equals(neutral)).findAny().get().getElement();
        final Move m1 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, neutral);
        final Move m2 = new BasicMove(LEGAL_BASE_DAMAGE_2, MOVE_NAME_1, neutral);
        final Move m3 = new BasicMove(LEGAL_BASE_DAMAGE_2, MOVE_NAME_1, neutral);
        final Move m4 = new BasicMove(LEGAL_BASE_DAMAGE_2, MOVE_NAME_2, element);
        final Move m5 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, neutral);
        assertEquals(m1, m5);
        assertNotEquals(m1, m3); // m1 and m3 have different damage
        assertNotEquals(m1, m4); // m1 and m4 have different element
        assertNotEquals(m2, m4); // m2 and m4 have different name
    }

    private int computeCorrectDamage(final DamageMove move, final Banion activeBanion, final Banion passiveBanion) {
        if (move.getElement().isWeaker(passiveBanion.getElement())) {
            return move.getBaseDamage() / move.getDamageMultiplier() + computeDamageFromStats(activeBanion, passiveBanion);
        } else if (move.getElement().isStronger(passiveBanion.getElement())) {
            return move.getBaseDamage() * move.getDamageMultiplier() + computeDamageFromStats(activeBanion, passiveBanion);
        } else {
            return move.getBaseDamage() + computeDamageFromStats(activeBanion, passiveBanion);
        }
    }

    private int computeDamageFromStats(final Banion activeBanion, final Banion passiveBanion) {
        return (int) Math.round(activeBanion.getAttack()) - (int) Math.round(passiveBanion.getDefence());
    }

}
