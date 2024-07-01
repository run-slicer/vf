package run.slicer.vf.impl;

import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.teavm.jso.JSBody;

public final class FernflowerLoggerImpl extends IFernflowerLogger {
    public static final IFernflowerLogger INSTANCE = new FernflowerLoggerImpl();

    private FernflowerLoggerImpl() {
    }

    @Override
    public void writeMessage(String message, Severity severity) {
        switch (severity) {
            case WARN -> warn(message);
            case ERROR -> error(message);
            default -> {}
        }
    }

    @Override
    public void writeMessage(String message, Severity severity, Throwable t) {
        error(message);
        error(t.toString());
    }

    @JSBody(params = "message", script = "if (console) console.warn(message);")
    private static native void warn(String message);

    @JSBody(params = "message", script = "if (console) console.error(message);")
    private static native void error(String message);
}
