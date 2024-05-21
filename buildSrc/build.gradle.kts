plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.git.versioning.plugin)
    implementation(libs.micronaut.aot.plugin)
    implementation(libs.micronaut.plugin)
    implementation(libs.shadow.plugin)
    implementation(libs.spotless.plugin)
}
