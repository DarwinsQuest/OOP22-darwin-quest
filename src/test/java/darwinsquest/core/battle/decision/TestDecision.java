package darwinsquest.core.battle.decision;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestDecision {

    @Test
    void testEquality() {
        final Decision d1 = new MoveDecision();
        final Decision d2 = new SwapDecision();
        final Decision d3 = new MoveDecision();
        final Decision d4 = new SwapDecision();
        assertNotEquals(d1, d2);
        assertEquals(d1, d3);
        assertEquals(d2, d4);
    }

}
