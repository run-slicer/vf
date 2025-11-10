package run.slicer.vf.impl;

import org.jetbrains.java.decompiler.main.extern.TextTokenVisitor;
import org.jetbrains.java.decompiler.struct.gen.FieldDescriptor;
import org.jetbrains.java.decompiler.struct.gen.MethodDescriptor;
import org.jetbrains.java.decompiler.util.token.TextRange;
import run.slicer.vf.Options;

public class TextTokenCollector extends TextTokenVisitor {
    private final Options.TokenCollector tokenCollector;

    public TextTokenCollector(TextTokenVisitor next, Options.TokenCollector tokenCollector) {
        super(next);
        this.tokenCollector = tokenCollector;
    }

    @Override
    public void start(String content) {
        super.start(content);
        tokenCollector.start(content);
    }

    @Override
    public void visitClass(TextRange range, boolean declaration, String name) {
        super.visitClass(range, declaration, name);
        tokenCollector.visitClass(range.start, range.length, declaration, name);
    }

    @Override
    public void visitField(TextRange range, boolean declaration, String className, String name, FieldDescriptor descriptor) {
        super.visitField(range, declaration, className, name, descriptor);
        tokenCollector.visitField(range.start, range.length, declaration, className, name, descriptor.descriptorString);
    }

    @Override
    public void visitMethod(TextRange range, boolean declaration, String className, String name, MethodDescriptor descriptor) {
        super.visitMethod(range, declaration, className, name, descriptor);
        tokenCollector.visitMethod(range.start, range.length, declaration, className, name, descriptor.toString());
    }

    @Override
    public void visitParameter(TextRange range, boolean declaration, String className, String methodName, MethodDescriptor methodDescriptor, int index, String name) {
        super.visitParameter(range, declaration, className, methodName, methodDescriptor, index, name);
        tokenCollector.visitParameter(range.start, range.length, declaration, className, methodName, methodDescriptor.toString(), index, name);
    }

    @Override
    public void visitLocal(TextRange range, boolean declaration, String className, String methodName, MethodDescriptor methodDescriptor, int index, String name) {
        super.visitLocal(range, declaration, className, methodName, methodDescriptor, index, name);
        tokenCollector.visitLocal(range.start, range.length, declaration, className, methodName, methodDescriptor.toString(), index, name);
    }

    @Override
    public void end() {
        super.end();
        tokenCollector.end();
    }
}
