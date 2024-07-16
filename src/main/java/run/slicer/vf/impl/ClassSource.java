package run.slicer.vf.impl;

import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class ClassSource extends AbstractContextSource {
    private final String[] names;
    private final IOutputSink sink;

    public ClassSource(String name, String[] resources, Function<String, byte[]> source, IOutputSink sink) {
        super(source);
        this.names = resources(name, resources);
        this.sink = sink;
    }

    @Override
    public Entries getEntries() {
        final List<Entry> entries = new ArrayList<>();
        for (final String name : this.names) {
            entries.add(new Entry(name, Entry.BASE_VERSION));
        }

        return new Entries(entries, List.of(), List.of());
    }

    @Override
    public IOutputSink createOutputSink(IResultSaver saver) {
        return this.sink;
    }

    public String[] names() {
        return this.names;
    }

    // quick workaround for a Vineflower bug/quirk:
    // inner classes need to be supplied in the base source, it does not find them in the library source
    private static String[] resources(String name, String[] resources) {
        final Set<String> names = new HashSet<>();
        names.add(name);

        for (final String resource : resources) {
            if (resource.startsWith(name + '$')) {
                names.add(resource);
            }
        }

        return names.toArray(new String[0]);
    }
}
