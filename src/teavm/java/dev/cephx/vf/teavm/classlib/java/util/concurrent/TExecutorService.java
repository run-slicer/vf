package dev.cephx.vf.teavm.classlib.java.util.concurrent;

import java.util.concurrent.Executor;

public interface TExecutorService extends Executor {
    TFuture<?> submit(Runnable task);

    void shutdown();
}
