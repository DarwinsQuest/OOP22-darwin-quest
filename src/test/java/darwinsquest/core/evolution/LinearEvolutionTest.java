package darwinsquest.core.evolution;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.core.gameobject.banion.BanionImpl;
import darwinsquest.core.gameobject.banion.BanionImpl.BanionStats;
import darwinsquest.core.gameobject.element.Element;
import darwinsquest.core.gameobject.element.Neutral;
import darwinsquest.core.gameobject.move.BasicMove;
import darwinsquest.core.gameobject.move.Move;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LinearEvolutionTest {

    public static final int XP_0 = 0;
    public static final int XP_5 = 5;
    private static final int XP_15 = 15;
    public static final int XP_20 = 20;
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    public static final int LEVEL_4 = 4;
    public static final int LEVEL_5 = 5;
    public static final int LEVEL_6 = 6;
    public static final int LEVEL_7 = 7;
    private static final int LEVEL_8 = 8;
    private static final int LEVEL_9 = 9;
    public static final int LEVEL_10 = 10;
    public static final int LEVEL_11 = 11;
    public static final int LEVEL_15 = 15;
    public static final int LEVEL_20 = 20;
    private static final String NAME = "Bob";
    private static final int MOVE_DAMAGE = LEVEL_10;
    private static final int DEFAULT_HP = 10;
    private static final int MAX_HP = 30;
    private static final double DEFAULT_ATK = 1.0;
    private static final double DEFAULT_DEF = 1.0;
    private static final double HP_MULTIPLIER = 0.15;
    private static final double ATK_MULTIPLIER = 0.15;
    private static final double DEF_MULTIPLIER = 0.15;
    private static final BanionStats DEFAULT_RECORD = new BanionStats(LEVEL_1, DEFAULT_HP, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF);
    private BanionStats lastRecord;
    private final Element neutral = new Neutral();
    private final Set<Move> moves = Set.of(new BasicMove(MOVE_DAMAGE, "1", neutral),
            new BasicMove(MOVE_DAMAGE, "2", neutral),
            new BasicMove(MOVE_DAMAGE, "3", neutral),
            new BasicMove(MOVE_DAMAGE, "4", neutral));

    @Test
    void evolveTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        assertFalse(b.evolve(banion -> banion.getLevel() > LEVEL_10));
        assertRecordEquals(DEFAULT_RECORD, b);
        lastRecord = new BanionStats(LEVEL_2,
                addPercentage(DEFAULT_RECORD.hp()),
                addPercentage(DEFAULT_RECORD.maxHp()),
                addPercentage(DEFAULT_RECORD.attack(), ATK_MULTIPLIER),
                addPercentage(DEFAULT_RECORD.defence(), DEF_MULTIPLIER));
        assertTrue(b.evolve(banion -> banion.getLevel() == 1));
        assertRecordEquals(lastRecord, b);
        assertFalse(b.evolve(banion -> false));
        assertRecordEquals(lastRecord, b);
        lastRecord = new BanionStats(LEVEL_3,
                addPercentage(lastRecord.hp()),
                addPercentage(lastRecord.maxHp()),
                addPercentage(lastRecord.attack(), ATK_MULTIPLIER),
                addPercentage(lastRecord.defence(), DEF_MULTIPLIER));
        assertTrue(b.evolve(banion -> banion.getLevel() % 2 == 0));
        assertRecordEquals(lastRecord, b);
        assertFalse(b.evolve(banion -> banion.getLevel() % 2 == 0));
        assertRecordEquals(lastRecord, b);
    }

    @Test
    void evolveToLevelTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        lastRecord = new BanionStats(LEVEL_10,
                MAX_HP,
                MAX_HP,
                addPercentage(DEFAULT_RECORD.attack(), ATK_MULTIPLIER, LEVEL_10 - 1),
                addPercentage(DEFAULT_RECORD.defence(), DEF_MULTIPLIER, LEVEL_10 - 1));
        assertTrue(b.evolveToLevel(LEVEL_10, banion -> banion.getLevel() <= LEVEL_10));
        assertRecordEquals(lastRecord, b);
        // Banion reached MAX_HP. HP Cannot further evolve.
        lastRecord = new BanionStats(LEVEL_15,
                MAX_HP,
                MAX_HP,
                addPercentage(lastRecord.attack(), ATK_MULTIPLIER, LEVEL_15 - LEVEL_10),
                addPercentage(lastRecord.defence(), DEF_MULTIPLIER, LEVEL_15 - LEVEL_10));
        assertTrue(b.evolveToLevel(LEVEL_15, banion -> banion.getLevel() < LEVEL_15));
        assertRecordEquals(lastRecord, b);
        assertThrows(IllegalArgumentException.class, () -> b.evolveToLevel(LEVEL_5, banion -> true));
        assertFalse(b.evolveToLevel(LEVEL_20, banion -> false));
        assertRollback(lastRecord, b);
    }

    @Test
    void rollbackTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        assertFalse(b.evolveToLevel(LEVEL_10, banion -> banion.getLevel() % 2 == 0));
        assertRollback(DEFAULT_RECORD, b);
    }

    @Test
    void evolveToLevelMultiMapTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        final MultiValuedMap<Predicate<Banion>, Integer> requirements = new ArrayListValuedHashMap<>();
        // From lvl 1 to lvl 7.
        requirements.putAll(banion -> banion.getLevel() <= LEVEL_3, List.of(LEVEL_1, LEVEL_2, LEVEL_3));
        requirements.putAll(banion -> banion.getLevel() != LEVEL_11, List.of(LEVEL_4, LEVEL_5, LEVEL_6));
        lastRecord = new BanionStats(LEVEL_7,
                addPercentage(DEFAULT_RECORD.hp(), LEVEL_7 - 1),
                addPercentage(DEFAULT_RECORD.maxHp(), LEVEL_7 - 1),
                addPercentage(DEFAULT_RECORD.attack(), ATK_MULTIPLIER, LEVEL_7 - 1),
                addPercentage(DEFAULT_RECORD.defence(), DEF_MULTIPLIER, LEVEL_7 - 1));
        assertTrue(b.evolveToLevel(LEVEL_7, requirements));
        assertRecordEquals(lastRecord, b);
        // From lvl 7 to lvl 9.
        requirements.putAll(banion -> true, List.of(LEVEL_7, LEVEL_8));
        lastRecord = new BanionStats(LEVEL_9,
                MAX_HP,
                MAX_HP,
                addPercentage(lastRecord.attack(), ATK_MULTIPLIER, LEVEL_9 - LEVEL_7),
                addPercentage(lastRecord.defence(), DEF_MULTIPLIER, LEVEL_9 - LEVEL_7));
        assertTrue(b.evolveToLevel(LEVEL_9, requirements));
        assertRecordEquals(lastRecord, b);
        // From lvl 9 to lvl 11.
        requirements.putAll(banion -> true, List.of(LEVEL_9, LEVEL_10));
        lastRecord = new BanionStats(LEVEL_11,
                MAX_HP,
                MAX_HP,
                addPercentage(lastRecord.attack(), ATK_MULTIPLIER, LEVEL_11 - LEVEL_9),
                addPercentage(lastRecord.defence(), DEF_MULTIPLIER, LEVEL_11 - LEVEL_9));
        assertTrue(b.evolveToLevel(LEVEL_11, requirements));
        assertRecordEquals(lastRecord, b);
    }

    @Test
    void multiMapErrorsTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        final MultiValuedMap<Predicate<Banion>, Integer> requirements = new ArrayListValuedHashMap<>();
        // Duplicates test.
        requirements.putAll(banion -> true, List.of(LEVEL_1, LEVEL_1, LEVEL_2));
        assertThrows(IllegalArgumentException.class, () -> b.evolveToLevel(LEVEL_3, requirements));
        requirements.clear();
        // Map with gap test.
        requirements.putAll(banion -> banion.getLevel() <= LEVEL_2, List.of(LEVEL_1, LEVEL_2));
        requirements.putAll(banion -> banion.getLevel() % LEVEL_3 == 0, List.of(LEVEL_3, LEVEL_4, LEVEL_6));
        assertThrows(IllegalArgumentException.class, () -> b.evolveToLevel(LEVEL_7, requirements));
        requirements.clear();
        // Rollback test.
        requirements.putAll(banion -> banion.getLevel() <= LEVEL_2, List.of(LEVEL_1, LEVEL_2));
        requirements.putAll(banion -> banion.getLevel() % 3 == 0, List.of(LEVEL_3, LEVEL_4));
        assertFalse(b.evolveToLevel(LEVEL_5, requirements));
        assertRollback(DEFAULT_RECORD, b);
        requirements.clear();
    }

    @Test
    void increaseXpTest() {
        final Banion b = new BanionImpl(neutral, NAME, DEFAULT_HP, DEFAULT_ATK, DEFAULT_DEF, moves);
        assertEquals(XP_0, b.getXp());
        b.increaseXp(XP_5);
        assertEquals(XP_5, b.getXp());
        assertRecordEquals(DEFAULT_RECORD, b);
        lastRecord = new BanionStats(LEVEL_2,
                addPercentage(DEFAULT_RECORD.hp(), 1),
                addPercentage(DEFAULT_RECORD.maxHp(), 1),
                addPercentage(DEFAULT_RECORD.attack(), ATK_MULTIPLIER, 1),
                addPercentage(DEFAULT_RECORD.defence(), DEF_MULTIPLIER, 1));
        b.increaseXp(XP_20);
        assertEquals(XP_5, b.getXp());
        assertRecordEquals(lastRecord, b);
        b.increaseXp(XP_15);
        assertEquals(XP_20, b.getXp());
        assertRecordEquals(lastRecord, b);
        lastRecord = new BanionStats(LEVEL_3,
                addPercentage(lastRecord.hp(), 1),
                addPercentage(lastRecord.maxHp(), 1),
                addPercentage(lastRecord.attack(), ATK_MULTIPLIER, 1),
                addPercentage(lastRecord.defence(), DEF_MULTIPLIER, 1));
        b.increaseXp(XP_5);
        assertEquals(XP_5, b.getXp());
        assertRecordEquals(lastRecord, b);
        assertThrows(IllegalArgumentException.class, () -> b.increaseXp(0));
        assertThrows(IllegalArgumentException.class, () -> b.increaseXp(-1));
    }

    private void assertRollback(final BanionStats record, final Banion banion) {
        assertEquals(record.level(), banion.getLevel());
        assertEquals(record.hp(), banion.getHp());
        assertEquals(record.maxHp(), banion.getMaxHp());
    }

    private void assertRecordEquals(final BanionStats record, final Banion banion) {
        final var currentRecord = new BanionStats(banion.getLevel(),
                banion.getHp(),
                banion.getMaxHp(),
                banion.getAttack(),
                banion.getDefence());
        assertEquals(record, currentRecord);
    }

    private int addPercentage(final int stat) {
        final double increase = 1 + HP_MULTIPLIER;
        return (int) Math.round(stat * increase);
    }

    private int addPercentage(final int stat, final int times) {
        int result = stat;
        for (int i = 0; i < times; i++) {
            result = addPercentage(result);
        }
        return result;
    }

    private double addPercentage(final double stat, final double multiplier) {
        final double increase = 1 + multiplier;
        return stat * increase;
    }

    private double addPercentage(final double stat, final double multiplier, final int times) {
        double result = stat;
        for (int i = 0; i < times; i++) {
            result = addPercentage(result, multiplier);
        }
        return result;
    }

}
