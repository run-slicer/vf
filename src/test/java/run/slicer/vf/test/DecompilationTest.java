package run.slicer.vf.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
        // node ./src/test/resources/decompile.js <class name 1> <class name 2> <class path 1> <class path 2> <...>
        final List<String> args = new ArrayList<>(List.of("node", "./src/test/resources/decompile.js"));
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
            final Process proc = new ProcessBuilder(args).inheritIO().redirectErrorStream(true).start();
            final var readerThread = Thread.startVirtualThread(() -> {
                try (final var reader = proc.inputReader()) {
                    reader.lines().forEach(line -> assertFalse(line.contains("Failed to load WASM module"), "WASM module failed to load"));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });

            final var code = proc.waitFor();
            assertEquals(0, code, "Expected zero exit code");

            readerThread.join();
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
