@file:Suppress("UnstableApiUsage")

import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    java
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

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
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
