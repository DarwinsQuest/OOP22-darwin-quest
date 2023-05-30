package darwinsquest;

import java.util.Set;

import darwinsquest.core.gameobject.banion.Banion;
import darwinsquest.config.BanionAdapterFactory;
import darwinsquest.config.CustomDeserializer;

/**
 * Factory that generates {@link Banion} objects.
 */
public class BanionFactory extends CustomDeserializer<Banion> implements TypeFactory<Banion> {

    /**
     * Default constructor.
     */
    public BanionFactory() {
        super(Banion.class, BanionAdapterFactory.class, ControllerImpl.PATH_BANIONS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Banion> createElements() {
        return readElements();
    }
}
