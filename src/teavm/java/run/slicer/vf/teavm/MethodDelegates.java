package run.slicer.vf.teavm;

import run.slicer.vf.teavm.classlib.java.util.concurrent.TConcurrentHashMap$KeySetView;
import org.jetbrains.java.decompiler.main.decompiler.CancelationManager;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer;
import org.jetbrains.java.decompiler.modules.renamer.ConverterHelper;
import org.jetbrains.java.decompiler.struct.StructClass;
import org.jetbrains.java.decompiler.util.DataInputFullStream;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.ConcurrentHashMap;

public final class MethodDelegates {
    private MethodDelegates() {
    }

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    public static <K> ConcurrentHashMap.KeySetView<K, Boolean> java_util_concurrent_ConcurrentHashMap_newKeySet() {
        return (ConcurrentHashMap.KeySetView<K, Boolean>) ((Object) (new TConcurrentHashMap$KeySetView<>(new ConcurrentHashMap<>(), Boolean.TRUE)));
    }

    public static IIdentifierRenamer org_jetbrains_java_decompiler_main_Fernflower_loadHelper(String ignored, IFernflowerLogger ignored1) {
        return new ConverterHelper();
    }

    public static void org_jetbrains_java_decompiler_main_rels_ClassWrapper_killThread(Thread ignored) {
        // emulate thread killing with a single-use cancellation checker
        CancelationManager.setCancelationChecker(() -> {
            CancelationManager.setCancelationChecker(() -> {});
            CancelationManager.cancel();
        });
    }
}
