plugins {
    `java-library`
    alias(libs.plugins.teavm) // order matters?
    alias(libs.plugins.blossom)
}

val thisVersion = "0.1.2"

group = "run.slicer"
version = "$thisVersion-${libs.versions.vineflower.get()}"
description = "A JavaScript port of the Vineflower decompiler."

repositories {
    mavenCentral()
}

dependencies {
    api(libs.vineflower)
    compileOnly(libs.teavm.core)
}

java.toolchain {
    languageVersion = JavaLanguageVersion.of(21)
}

teavm.js {
    mainClass = "run.slicer.vf.Main"
    moduleType = org.teavm.gradle.api.JSModuleType.ES2015
    // obfuscated = false
    // optimization = org.teavm.gradle.api.OptimizationLevel.NONE
}

tasks {
    register<Copy>("copyDist") {
        group = "build"

        from("README.md", "LICENSE", "LICENSE-VF", generateJavaScript, "vf.d.ts")
        into("dist")

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
