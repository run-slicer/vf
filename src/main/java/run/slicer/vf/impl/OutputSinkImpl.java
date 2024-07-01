package run.slicer.vf.impl;

import org.jetbrains.java.decompiler.main.extern.IContextSource;

public final class OutputSinkImpl implements IContextSource.IOutputSink {
    private final ThreadLocal<String> output = new ThreadLocal<>();
    private final String name;

    public OutputSinkImpl(String name) {
        this.name = name;
    }

    @Override
    public void begin() {
    }

    @Override
    public void acceptClass(String qualifiedName, String fileName, String content, int[] mapping) {
        if (this.name.equals(qualifiedName)) {
            this.output.set(content);
        }
    }

    @Override
    public void acceptDirectory(String directory) {
    }

    @Override
    public void acceptOther(String path) {
    }

    @Override
    public void close() {
    }

    public String output() {
        return this.output.get();
    }

    public String name() {
        return this.name;
    }
}
