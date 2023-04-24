package darwinsquest.core;

import java.util.function.IntPredicate;

/**
 * Class that implements a {@link Banion}.
 */
public class BanionImpl implements Banion {

    private int hp;
    private int atk;
    private int def;

    /**
     * Costructor that creates a {@link BanionImpl}.
     * @param hp Hit points, represents health.
     * @param atk Attack, represents damage tendency.
     * @param def Defense, represents resistence hit tendency.
     * @throws IllegalArgumentException Stats can't be init as negative or zero.
     */
    public BanionImpl(final int hp, final int atk, final int def) {
        if (hp <= 0 || atk <= 0 || def <= 0) {
            throw new IllegalArgumentException("Stats can't be init as negative or zero.");
        }
        this.hp = hp;
        this.atk = atk;
        this.def = def;
    }

    private void testStat(final int stat, final IntPredicate predicate, final String message) {
        if (predicate.negate().test(stat)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getHp() {
        return hp;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If hit points set to negative.
     */
    @Override
    public void setHp(final int amount) {
        testStat(amount, hp -> hp >= 0, "Banion hp can't be set to a value < 0.");
        hp = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAtk() {
        return atk;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If attack set to negative or zero.
     */
    @Override
    public void setAtk(final int amount) {
        testStat(amount, atk -> atk > 0, "Banion attack can't be set to a value <= 0.");
        atk = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDef() {
        return def;
    }

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException If defense set to negative or zero.
     */
    @Override
    public void setDef(final int amount) {
        testStat(amount, def -> def > 0, "Banion defense can't be set to a value <= 0.");
        def = amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return hp > 0;
    }
}
