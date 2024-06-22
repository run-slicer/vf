package dev.cephx.vf.teavm.classlib.java.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

final class DummyExecutorService implements ExecutorService {
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
            return new Task<>(task.call(), null);
        } catch (Throwable t) {
            return new Task<>(null, t);
        }
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        try {
            task.run();
        } catch (Throwable t) {
            return new Task<>(null, t);
        }

        return new Task<>(result, null);
    }

    @Override
    public Future<?> submit(Runnable task) {
        try {
            task.run();
        } catch (Throwable t) {
            return new Task<>(null, t);
        }

        return new Task<>(null, null);
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

    private record Task<T>(T value, Throwable error) implements Future<T> {
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
}
