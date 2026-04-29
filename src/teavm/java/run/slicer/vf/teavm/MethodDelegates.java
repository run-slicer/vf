package run.slicer.vf.teavm;

import org.vineflower.kt.metadata.ProtoBuf;
import org.vineflower.kt.metadata.ProtoBuf$Contract$Builder;
import org.vineflower.kt.metadata.ProtoBuf$Expression$Builder;
import org.vineflower.kt.metadata.ProtoBuf$VersionRequirementTable$Builder;
import org.vineflower.kt.metadata.jvm.JvmProtoBuf;
import org.vineflower.kt.metadata.jvm.JvmProtoBuf$JvmFieldSignature$Builder;
import run.slicer.vf.teavm.classlib.java.lang.TThreadLocal;
import run.slicer.vf.teavm.classlib.java.util.concurrent.TConcurrentHashMap$KeySetView;
import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger;
import org.jetbrains.java.decompiler.main.extern.IIdentifierRenamer;
import org.jetbrains.java.decompiler.modules.renamer.ConverterHelper;

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

    public static void java_lang_Runtime_gc(Runtime ignored) {
        TThreadLocal.removeAll();
    }

    public static ProtoBuf$VersionRequirementTable$Builder org_vineflower_kt_metadata_ProtoBuf$VersionRequirementTable_toBuilder(ProtoBuf.VersionRequirementTable ignored) {
        return new ProtoBuf$VersionRequirementTable$Builder();
    }

    public static ProtoBuf$Contract$Builder org_vineflower_kt_metadata_ProtoBuf$Contract_toBuilder(ProtoBuf.Contract ignored) {
        return new ProtoBuf$Contract$Builder();
    }

    public static ProtoBuf$Contract$Builder org_vineflower_kt_metadata_ProtoBuf$Contract_newBuilder(ProtoBuf.Contract ignored) {
        return new ProtoBuf$Contract$Builder();
    }

    public static ProtoBuf$Expression$Builder org_vineflower_kt_metadata_ProtoBuf$Expression_toBuilder(ProtoBuf.Expression ignored) {
        return new ProtoBuf$Expression$Builder();
    }

    public static JvmProtoBuf$JvmFieldSignature$Builder org_vineflower_kt_metadata_jvm_JvmProtoBuf$JvmFieldSignature_toBuilder(JvmProtoBuf.JvmFieldSignature ignored) {
        return new JvmProtoBuf$JvmFieldSignature$Builder();
    }

    public static JvmProtoBuf$JvmFieldSignature$Builder org_vineflower_kt_metadata_jvm_JvmProtoBuf$JvmFieldSignature_newBuilder(JvmProtoBuf.JvmFieldSignature ignored) {
        return new JvmProtoBuf$JvmFieldSignature$Builder();
    }
}
