package darwinsquest.core;

import java.util.Objects;
import java.util.Random;
import java.util.function.IntSupplier;
import java.util.random.RandomGenerator;

/**
 * A polyhedral die implementation of an {@link IntSupplier}.
 * @see <a href="https://en.wikipedia.org/wiki/Dice#Polyhedral_dice">Polyhedral die</a>.
 */
public class Die implements IntSupplier {

    private static final int DEFAULT_FACES = 6;
    private static final int MIN_FACES = 4;
    private final int faces;
    private final RandomGenerator generator;

    /**
     * Default constructor.
     */
    public Die() {
        generator = new Random();
        faces = DEFAULT_FACES;
    }

    /**
     * Custom constructor for the class {@link Die}.
     * This constructor allows the instantiation of strictly
     * polyhedral dice.
     * @param faces the amount of the custom {@link Die}'s faces.
     */
    public Die(final int faces) throws IllegalStateException {
        generator = new Random();
        if (!isDieLegal(faces)) {
            throw new IllegalStateException("Not a platonic solid.");
        }
        this.faces = faces;
    }

    /**
     * Getter for a {@link Die} number of faces.
     * @return the number of faces.
     */
    public int getFaces() {
        return faces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAsInt() {
        return generator.nextInt(faces) + 1;
    }

    private boolean isDieLegal(final int faces) {
        return faces >= MIN_FACES && faces % 2 == 0;    // A die with an odd number of faces is not a polyhedron.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Die die = (Die) o;
        return faces == die.faces;
    }

    @Override
    public int hashCode() {
        return Objects.hash(faces, generator);
    }
}
