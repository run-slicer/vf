package run.slicer.vf.teavm.classlib.java.util.concurrent;

public class TForkJoinPool extends DummyExecutorService {
    public TForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
    }

    @Override
    public TForkJoinTask<?> submit(Runnable task) {
        try {
            task.run();
        } catch (Throwable t) {
            return new TForkJoinTask<>(null, t);
        }

        return new TForkJoinTask<>(null, null);
    }

    public interface ForkJoinWorkerThreadFactory {
    }
}
