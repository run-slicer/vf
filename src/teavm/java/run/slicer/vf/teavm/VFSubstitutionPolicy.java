package run.slicer.vf.teavm;

import org.teavm.extension.Autoregistered;
import org.teavm.extension.spi.substitution.SimpleSubstitutionPolicy;
import org.teavm.extension.spi.substitution.SubstitutionSink;

import java.util.Set;

@Autoregistered
public class VFSubstitutionPolicy extends SimpleSubstitutionPolicy {
    private static final Set<String> CLASSLIB_SUBSTITUTIONS = Set.of(
            "java.util.concurrent.ConcurrentHashMap$KeySetView",
            "java.util.concurrent.Executors",
            "java.util.concurrent.ExecutorService",
            "java.util.concurrent.Future",
            "java.util.concurrent.TimeoutException",
            "java.util.StringJoiner",
            "java.util.concurrent.ForkJoinPool",
            "java.util.concurrent.ForkJoinPool$ForkJoinWorkerThreadFactory",
            "java.util.concurrent.ForkJoinTask",
            "java.lang.ThreadLocal"
    );

    @Override
    public void contribute(SubstitutionSink sink) {
        sink.selectClasses(CLASSLIB_SUBSTITUTIONS::contains)
                .packagePrefix("run.slicer.vf.teavm.classlib.")
                .simpleNamePrefix("T");

        // manual source patches to the decompiler
        sink.selectClasses(named("org.jetbrains.java.decompiler.main.rels.ClassWrapper"))
                .simpleNameSuffix("Patch");
    }
}
