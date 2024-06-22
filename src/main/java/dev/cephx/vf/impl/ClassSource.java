package dev.cephx.vf.impl;

import org.jetbrains.java.decompiler.main.extern.IResultSaver;

import java.util.List;
import java.util.function.Function;

public final class ClassSource extends AbstractContextSource {
    private final String name;
    private final IOutputSink sink;

    public ClassSource(String name, Function<String, byte[]> source, IOutputSink sink) {
        super(source);
        this.name = name;
        this.sink = sink;
    }

    @Override
    public Entries getEntries() {
        return new Entries(List.of(new Entry(this.name, Entry.BASE_VERSION)), List.of(), List.of());
    }

    @Override
    public IOutputSink createOutputSink(IResultSaver saver) {
        return this.sink;
    }

    public String name() {
        return this.name;
    }
}
