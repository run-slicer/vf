package run.slicer.vf.teavm.classlib.java.util.concurrent;

public class TExecutors {
    @SuppressWarnings({"RedundantCast", "DataFlowIssue"})
    public static TExecutorService newFixedThreadPool(int ignored) {
        return (TExecutorService) ((Object) new DummyExecutorService());
    }
}
