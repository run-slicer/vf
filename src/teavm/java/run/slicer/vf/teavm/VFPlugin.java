package run.slicer.vf.teavm;

import org.teavm.vm.spi.TeaVMHost;
import org.teavm.vm.spi.TeaVMPlugin;

public final class VFPlugin implements TeaVMPlugin {
    @Override
    public void install(TeaVMHost host) {
        host.add(new MethodStubTransformer());
        host.add(new MethodDelegationTransformer());
    }
}
