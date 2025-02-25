package run.slicer.vf.teavm.classlib.java.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public record DummyTask<T>(T value, Throwable error) implements Future<T> {
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return true;
    }

    @Override
    public T get() throws ExecutionException {
        if (this.error != null) {
            throw new ExecutionException(this.error);
        }

        return this.value;
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws ExecutionException {
        return this.get();
    }
}
