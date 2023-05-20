package darwinsquest.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import darwinsquest.core.element.Fire;
import darwinsquest.core.element.Grass;
import darwinsquest.core.element.Water;

class TestMove {

    private static final int ILLEGAL_BASE_DAMAGE = -10;
    private static final int LEGAL_BASE_DAMAGE_1 = 20;
    private static final int LEGAL_BASE_DAMAGE_2 = 10;
    private static final String MOVE_NAME_1 = "Fireball";
    private static final String MOVE_NAME_2 = "Splash";
    private static final String BANION_NAME_1 = "Matchy";
    private static final String BANION_NAME_2 = "Pond";
    private static final String BANION_NAME_3 = "Grassy";
    private static final int HP_1 = 100;
    private static final int HP_2 = 20;
    private static final int HP_3 = 15;

    @Test
    void testMoveCreation() {
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(ILLEGAL_BASE_DAMAGE, MOVE_NAME_1, new Fire()));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, null, new Fire()));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, "", new Fire()));
        assertThrows(IllegalArgumentException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, "  ", new Fire()));
        assertThrows(NullPointerException.class, () -> new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, null));

        final DamageMove move = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Fire()); 
        assertEquals(LEGAL_BASE_DAMAGE_1, move.getDamage());
        assertEquals(new Fire(), move.getElement());
        assertEquals(MOVE_NAME_1, move.getName());
    }

    @Test
    void testPerformMove() {
        final DamageMove move = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Fire());
        final Banion banion1 = new BanionImpl(new Fire(), BANION_NAME_1, HP_1);
        final Banion banion2 = new BanionImpl(new Water(), BANION_NAME_2, HP_2);
        final Banion banion3 = new BanionImpl(new Grass(), BANION_NAME_3, HP_3);
        move.perform(banion1);
        assertEquals(banion1.getHp(), HP_1 - move.getDamage());
        move.perform(banion2);
        assertEquals(banion2.getHp(), HP_2 - move.getDamage());
        move.perform(banion3);
        assertEquals(banion3.getHp(), 0);
    }

    @Test
    void testEquality() {
        final Move m1 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Fire());
        final Move m2 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_2, new Water());
        final Move m3 = new BasicMove(LEGAL_BASE_DAMAGE_2, MOVE_NAME_1, new Fire());
        final Move m4 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Water());
        final Move m5 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Fire());
        assertEquals(m1, m5);
        assertNotEquals(m1, m3); // m1 and m3 have different damage
        assertNotEquals(m1, m4); // m1 and m4 have different element
        assertNotEquals(m2, m4); // m2 and m4 have different name
    }

    @Test
    void testCopy() {
        final Move m1 = new BasicMove(LEGAL_BASE_DAMAGE_1, MOVE_NAME_1, new Fire());
        final Move m2 = new BasicMove(LEGAL_BASE_DAMAGE_2, MOVE_NAME_2, new Water());
        final Move m1Copy = m1.copy();
        final Move m2Copy = m2.copy();
        assertEquals(m1, m1Copy);
        assertEquals(m2, m2Copy);
    }

}
