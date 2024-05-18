plugins { id("buildlogic.java-application-conventions") }

dependencies { implementation(project(":libs:common")) }

application { mainClass = "io.github.carlomicieli.App" }
