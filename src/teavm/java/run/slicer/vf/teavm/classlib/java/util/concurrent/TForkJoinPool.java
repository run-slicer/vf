package run.slicer.vf.teavm.classlib.java.util.concurrent;

public class TForkJoinPool extends DummyExecutorService {
    public TForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
    }

    @Override
    public DummyTask<?> submit(Runnable task) {
        try {
            task.run();
        } catch (Throwable t) {
            return new DummyTask<>(null, t);
        }

        return new DummyTask<>(null, null);
    }

    public interface ForkJoinWorkerThreadFactory {
    }
}
