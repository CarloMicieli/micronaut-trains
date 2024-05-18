@file:Suppress("UnstableApiUsage")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    java
    id("com.diffplug.spotless")
}

configurations {
    all {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    implementation {
        resolutionStrategy {
            failOnVersionConflict()
        }
    }
}

configurations {
    compileClasspath {
        resolutionStrategy.activateDependencyLocking()
    }
}

repositories {
    mavenCentral()
}

val micronautVersion: String by project
dependencies {
    implementation(platform("io.micronaut.platform:micronaut-platform:${micronautVersion}"))
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks {
    withType<JavaCompile> {
        options.isIncremental = true
        options.isFork = true
        options.isFailOnError = true

        options.compilerArgs.addAll(
            arrayOf(
                "-Xlint:all",
                "-Xlint:-processing",
                "-Werror"
            )
        )
    }
    test {
        useJUnitPlatform()

        minHeapSize = "512m"
        maxHeapSize = "1G"
        failFast = false
        ignoreFailures = false

        testLogging {
            showStandardStreams = false
            events(PASSED, FAILED, SKIPPED)
            showExceptions = true
            showCauses = true
            showStackTraces = true
            exceptionFormat = FULL
        }
    }
}

spotless {
    java {
        // optional: you can specify import groups directly
        // note: you can use an empty string for all the imports you didn't specify explicitly,
        // '|' to join group without blank line, and '\\#` prefix for static imports
        importOrder("java|javax", "io.github.carlomicieli", "", "\\#io.github.carlomicieli", "\\#")
        removeUnusedImports()

        palantirJavaFormat("2.46.0")

        formatAnnotations()  // fixes formatting of type annotations

        licenseHeaderFile("${project.rootDir}/.spotless/HEADER.txt")

        toggleOffOn("fmt:off", "fmt:on")
        indentWithSpaces()
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        endWithNewline()
        ktfmt("0.49")
        indentWithSpaces()
        trimTrailingWhitespace()
    }
}
