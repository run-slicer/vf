plugins {
    `java-library`
    alias(libs.plugins.teavm) // order matters?
    alias(libs.plugins.blossom)
}

val thisVersion = "0.2.3"

group = "run.slicer"
version = "$thisVersion-${libs.versions.vineflower.get()}"
description = "A JavaScript port of the Vineflower decompiler."

repositories {
    mavenCentral()
    maven("https://teavm.org/maven/repository")
}

val vineflower by configurations.creating
configurations.api.configure { extendsFrom(vineflower) }

dependencies {
    vineflower(libs.vineflower)
    compileOnly(libs.teavm.core)

    // expand plugins into the compilation classpath
    implementation(provider {
        zipTree(vineflower.singleFile).filter { it.extension == "jar" }
    })
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(21)
}

teavm.wasmGC {
    mainClass = "run.slicer.vf.Main"
    modularRuntime = true
    /*obfuscated = false
    optimization = org.teavm.gradle.api.OptimizationLevel.NONE
    disassembly = true*/
}

/*tasks.disasmWasmGC {
    html = false
}*/

tasks {
    register<Copy>("copyDist") {
        group = "build"

        from(
            "README.md", "LICENSE", "LICENSE-VF", "vf.js", "vf.d.ts",
            copyWasmGCRuntime, generateWasmGC
        )
        into("dist")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        doLast {
            file("dist/package.json").writeText(
                """
                    {
                      "name": "@run-slicer/vf",
                      "version": "${project.version}",
                      "description": "A JavaScript port of the Vineflower decompiler (https://github.com/Vineflower/vineflower).",
                      "main": "vf.js",
                      "types": "vf.d.ts",
                      "keywords": [
                        "decompiler",
                        "java",
                        "decompilation",
                        "vf",
                        "vineflower"
                      ],
                      "author": "run-slicer",
                      "license": "Apache License 2.0, MIT"
                    }
                """.trimIndent()
            )
        }
    }

    build {
        dependsOn("copyDist")
    }

    clean {
        delete("dist")
    }
}

val kotlin.reflect.KClass<*>.bytes: ByteArray
    get() = (java.classLoader ?: ClassLoader.getSystemClassLoader())
        .getResourceAsStream(java.name.replace('.', '/') + ".class")!!
        .use(`java.io`.InputStream::readAllBytes)

val ByteArray.base64String: String
    get() = `java.util`.Base64.getEncoder().encodeToString(this)

sourceSets.teavm {
    blossom {
        javaSources {
            property("java_lang_Object", Object::class.bytes.base64String)
        }
    }
}
