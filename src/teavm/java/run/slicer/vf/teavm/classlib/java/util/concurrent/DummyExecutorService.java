package run.slicer.vf.teavm.classlib.java.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class DummyExecutorService implements ExecutorService {
    private boolean shutdown = false;

    @Override
    public void shutdown() {
        this.shutdown = true;
    }

    @Override
    public List<Runnable> shutdownNow() {
        this.shutdown = true;
        return List.of();
    }

    @Override
    public boolean isShutdown() {
        return this.shutdown;
    }

    @Override
    public boolean isTerminated() {
        return this.shutdown;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) {
        return true;
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        try {
            return new DummyTask<>(task.call(), null);
        } catch (Throwable t) {
            return new DummyTask<>(null, t);
        }
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        try {
            task.run();
        } catch (Throwable t) {
            return new DummyTask<>(null, t);
        }

        return new DummyTask<>(result, null);
    }

    @Override
    public Future<?> submit(Runnable task) {
        try {
            task.run();
        } catch (Throwable t) {
            return new DummyTask<>(null, t);
        }

        return new DummyTask<>(null, null);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
