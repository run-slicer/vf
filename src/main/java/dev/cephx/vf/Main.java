package dev.cephx.vf;

import dev.cephx.vf.impl.*;
import org.jetbrains.java.decompiler.main.Fernflower;
import org.jetbrains.java.decompiler.main.extern.IFernflowerPreferences;
import org.teavm.jso.JSExport;
import org.teavm.jso.core.JSObjects;

import java.util.HashMap;
import java.util.Map;

public class Main {
    @JSExport
    public static String decompile(String name, Options options) {
        return decompile0(name, options == null || JSObjects.isUndefined(options) ? JSObjects.create() : options);
    }

    private static String decompile0(String name, Options options) {
        final Map<String, Object> options0 = new HashMap<>(IFernflowerPreferences.DEFAULTS);
        options0.putAll(options.rawOptions());

        final var outputSink = new OutputSinkImpl(name);

        final var fernflower = new Fernflower(ResultSaverImpl.INSTANCE, options0, FernflowerLoggerImpl.INSTANCE);
        fernflower.addSource(new ClassSource(name, options.source()::get, outputSink));
        fernflower.addLibrary(new ResourceSource(options.resources(), options.source()::get));
        fernflower.decompileContext();

        return outputSink.output();
    }
}
