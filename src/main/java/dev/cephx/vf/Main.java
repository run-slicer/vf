package dev.cephx.vf;

import dev.cephx.vf.impl.*;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.teavm.interop.Async;
import org.teavm.interop.AsyncCallback;
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
        return new JSPromise<>((resolve, reject) -> {
            new Thread(() -> {
                try {
                    final Map<String, Object> options0 = new HashMap<>(IFernflowerPreferences.DEFAULTS);
                    options0.putAll(options.rawOptions());

                    final var outputSink = new OutputSinkImpl(name);

                    final var fernflower = new Fernflower(ResultSaverImpl.INSTANCE, options0, FernflowerLoggerImpl.INSTANCE);
                    fernflower.addSource(new ClassSource(name, name0 -> source0(options, name0), outputSink));
                    fernflower.addLibrary(new ResourceSource(options.resources(), name0 -> source0(options, name0)));
                    fernflower.decompileContext();

                    resolve.accept(JSString.valueOf(outputSink.output()));
                } catch (Throwable e) {
                    reject.accept(e);
                }
            }).start();
        });
    }

    @Async
    private static native byte[] source0(Options options, String name);

    private static void source0(Options options, String name, AsyncCallback<byte[]> callback) {
        options.source(name)
                .then(b -> {
                    callback.complete(unwrapByteArray(b));
                    return null;
                })
                .catchError(err -> {
                    callback.error(new Exception(err.toString()));
                    return null;
                });
    }

    @JSBody(params = {"data"}, script = "return data;")
    private static native @JSByRef byte[] unwrapByteArray(Uint8Array data);
}
