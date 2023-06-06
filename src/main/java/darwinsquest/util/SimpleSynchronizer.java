package darwinsquest.util;

/**
 * Class that represents a thread synchronizer.
 */
public class SimpleSynchronizer implements Synchronizer {

    private boolean signalArrived;

    /**
     * Default constructor.
     */
    public SimpleSynchronizer() {
        signalArrived = false;
    }

    /**
     * Makes this thread wait until signal arrives.
     * @throws InterruptedException if the thread had been interrupted yet.
     */
    @Override
    public synchronized void waitForSignal() throws InterruptedException {
        while (!signalArrived) {
            this.wait();
        }
        signalArrived = false;
    }

    /**
     * Sends a signal to this thread.
     */
    @Override
    public synchronized void signal() {
        signalArrived = true;
        notifyAll();
    }
}
