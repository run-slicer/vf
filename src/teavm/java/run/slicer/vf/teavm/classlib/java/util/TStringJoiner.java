package run.slicer.vf.teavm.classlib.java.util;

public final class TStringJoiner {
    private final CharSequence delimiter, prefix, suffix;
    private final StringBuilder builder = new StringBuilder();
    private boolean wroteFirst = false;

    public TStringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        this.delimiter = delimiter;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public TStringJoiner add(CharSequence newElement) {
        if (!this.wroteFirst) {
            this.wroteFirst = true;
        } else {
            this.builder.append(this.delimiter);
        }

        this.builder.append(newElement);
        return this;
    }

    @Override
    public String toString() {
        return this.prefix + this.builder.toString() + this.suffix;
    }
}
