plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "micronaut-trains"
include("api")
include(":libs:common")
include(":libs:catalog")

