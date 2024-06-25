package dev.cephx.vf;

import org.teavm.jso.JSBody;
import org.teavm.jso.JSByRef;
import org.teavm.jso.JSFunctor;
import org.teavm.jso.JSObject;
import org.teavm.jso.core.JSPromise;
import org.teavm.jso.typedarrays.Uint8Array;

import java.util.HashMap;
import java.util.Map;

public interface Options extends JSObject {
    @JSBody(script = "return this.options ? Object.entries(this.options) : [];")
    Option[] options();

    default Map<String, String> rawOptions() {
        final Map<String, String> options = new HashMap<>();
        for (final Options.Option option : this.options()) {
            options.put(option.name(), option.value());
        }

        return options;
    }

    @JSBody(params = {"name"}, script = "return this.source ? this.source(name) : Promise.resolve(null);")
    JSPromise<Uint8Array> source(String name);

    @JSBody(script = "return this.resources || [];")
    String[] resources();

    interface Option extends JSObject {
        @JSBody(script = "return this[0];")
        String name();

        @JSBody(script = "return this[1];")
        String value();
    }
}
