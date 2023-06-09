package darwinsquest.util;

public abstract class AbstractEObserver<T> implements EObserver<T> {

    private boolean isTerminated;

    @Override
    public void update(ESource<? extends T> s, T arg) {
        updateOperation(s, arg);
        isTerminated = true;
    }

    public abstract void updateOperation(ESource<? extends T> s, T arg);

    public boolean isTerminated() {
        return isTerminated;
    }

}
