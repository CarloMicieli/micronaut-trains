plugins { id("buildlogic.java-library-conventions") }

dependencies {
  implementation(libs.slugify)
  api(libs.nv.i18n)
}
