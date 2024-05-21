plugins { id("buildlogic.java-application-conventions") }

dependencies {
  implementation(project(":libs:common"))
  implementation(project(":libs:catalog"))
}

application { mainClass = "io.github.carlomicieli.App" }
