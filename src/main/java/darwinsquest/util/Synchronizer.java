package darwinsquest.util;

/**
 * Interface that represents a synchronization with class.
 */
public interface Synchronizer {

    /**
     * Enters in wait state until signal arrives.
     * @throws InterruptedException if thread was interrupted.
     */
    void waitForSignal() throws InterruptedException;

    /**
     * Signal confirms that execution can restart.
     */
    void signal();
}
