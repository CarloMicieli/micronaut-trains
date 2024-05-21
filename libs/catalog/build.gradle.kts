plugins { id("buildlogic.micronaut-library-conventions") }

dependencies {
  implementation(project(":libs:common"))
  api(libs.nv.i18n)
}
