plugins {
    id("buildlogic.java-common-conventions")
    id("io.micronaut.library")
}

dependencies {
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
}
