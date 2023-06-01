package darwinsquest.generation;

import java.util.Set;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.config.BanionAdapterFactory;
import darwinsquest.config.CustomDeserializer;

/**
 * Factory that generates {@link Banion} objects.
 */
public class BanionFactory extends CustomDeserializer<Banion> implements SetFactory<Banion> {

    /**
     * Path to {@link darwinsquest.core.gameobject.banion.Banion} definitions.
     */
    public static final String PATH_BANIONS = "config/banions.json";

    /**
     * Default constructor.
     */
    public BanionFactory() {
        super(Banion.class, BanionAdapterFactory.class, PATH_BANIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Banion> createElements() {
        return readElements();
    }
}
