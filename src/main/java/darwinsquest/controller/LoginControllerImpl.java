package darwinsquest.controller;

import darwinsquest.core.gameobject.entity.PlayerImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;

/**
 * Class that represents a login controller.
 */
public final class LoginControllerImpl implements LoginController {

    private final ControllerManager controller;

    /**
     * Default constructor.
     * @param controller the main controller.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "Main controller is needed to separate concerns.")
    public LoginControllerImpl(final ControllerManager controller) {
        this.controller = Objects.requireNonNull(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValidUsername(final String username) {
        return PlayerImpl.isNameValid(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void login(final String username) {
        controller.setPlayer(new PlayerImpl(username));
    }
}
