import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage
import io.micronaut.gradle.docker.NativeImageDockerfile

plugins {
    id("buildlogic.java-common-conventions")
    id("com.bmuschko.docker-remote-api")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.aot")
    id("io.micronaut.application")
}

dependencies {
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    annotationProcessor("io.micronaut:micronaut-http-validation")
    compileOnly("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.validation:micronaut-validation")
    implementation("io.micronaut:micronaut-management")
    implementation("jakarta.validation:jakarta.validation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("org.yaml:snakeyaml")
    testImplementation("io.micronaut:micronaut-http-client")
}

graalvmNative.toolchainDetection = false
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("io.github.carlomicieli.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }

}

tasks.named<DockerBuildImage>("dockerBuildNative") {
    images.add("micronaut-trains/server-native:${project.version}")
    images.add("micronaut-trains/server-native:latest")
}

tasks.named<NativeImageDockerfile>("dockerfileNative") {
    jdkVersion = "21"
    exposePort(8080, 8081)
}
