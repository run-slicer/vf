package run.slicer.vf;

import run.slicer.vf.impl.*;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.jetbrains.java.decompiler.main.extern.TextTokenVisitor;
import org.teavm.jso.JSBody;
import org.teavm.jso.JSByRef;
import org.teavm.jso.JSExport;
import org.teavm.jso.core.JSObjects;
import org.teavm.jso.core.JSPromise;
import org.teavm.jso.core.JSString;
import org.teavm.jso.typedarrays.Uint8Array;

import java.util.HashMap;
import java.util.Map;

public class Main {
    @JSExport
    public static JSPromise<JSString> decompile(String name, Options options) {
        return decompile0(name, options == null || JSObjects.isUndefined(options) ? JSObjects.create() : options);
    }

    private static JSPromise<JSString> decompile0(String name, Options options) {
        return JSPromise.callAsync(() -> {
            final Map<String, Object> options0 = new HashMap<>(IFernflowerPreferences.DEFAULTS);
            options0.putAll(options.rawOptions());

            final var outputSink = new OutputSinkImpl(name);

            final var fernflower = new Fernflower(ResultSaverImpl.INSTANCE, options0, FernflowerLoggerImpl.INSTANCE);

            if (options.tokenCollector() != null) {
                TextTokenVisitor.addVisitor(next -> new TextTokenCollector(next, options.tokenCollector()));
            }

            fernflower.addSource(new ClassSource(name, options.resources(), name0 -> source0(options, name0), outputSink));
            fernflower.addLibrary(new ResourceSource(options.resources(), name0 -> source0(options, name0)));
            fernflower.decompileContext();
            fernflower.clearContext();

            return JSString.valueOf(outputSink.output());
        });
    }

    private static byte[] source0(Options options, String name) {
        final Uint8Array b = options.source(name).await();
        return b == null || JSObjects.isUndefined(b) ? null : unwrapByteArray(b);
    }

    @JSBody(params = {"data"}, script = "return data;")
    private static native @JSByRef(optional = true) byte[] unwrapByteArray(Uint8Array data);
}
