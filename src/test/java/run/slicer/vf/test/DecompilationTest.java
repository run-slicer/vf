package run.slicer.vf.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecompilationTest {
    @TempDir
    Path tmpDir;

    @Test
    public void decompileJavaLangObject() {
        runDecompile(Object.class);
    }

    @Test
    public void decompileJavaLangThread() {
        runDecompile(Thread.class, Thread.Builder.class, Thread.UncaughtExceptionHandler.class, Thread.State.class);
    }

    public void runDecompile(Class<?>... classes) {
        // node ./scripts/decompile.js <class name 1> <class name 2> <class path 1> <class path 2> <...>
        final List<String> args = new ArrayList<>(List.of("node", "./scripts/decompile.js"));
        for (final Class<?> clazz : classes) {
            args.add(internalName(clazz));
        }
        try {
            for (final Class<?> clazz : classes) {
                final var file = tmpDir.resolve(internalName(clazz) + ".class");
                Files.createDirectories(file.getParent());

                final var classBytes = classBytes(clazz);
                Files.write(file, classBytes);
                args.add(file.toAbsolutePath().toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            final int code = new ProcessBuilder(args).inheritIO().start().waitFor();
            assertEquals(0, code);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] classBytes(Class<?> clazz) {
        final var classLoader = Objects.requireNonNullElse(clazz.getClassLoader(), ClassLoader.getPlatformClassLoader());

        final var resource = internalName(clazz) + ".class";
        try (final var is = classLoader.getResourceAsStream(resource)) {
            if (is == null) {
                throw new RuntimeException("Resource not found: " + resource);
            }

            return is.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String internalName(Class<?> clazz) {
        return clazz.getName().replace('.', '/');
    }
}
