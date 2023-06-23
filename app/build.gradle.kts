@file:Suppress("UnstableApiUsage")

plugins {
  id("voice.app")
  id("android-convention")
  id("voice.compose")
}

android {
  namespace = "com.aliak.bookvoice"
  packaging {
    resources {
      resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(libs.androidxCore)
  implementation(libs.lifecycle)

  testImplementation(libs.junit)
  androidTestImplementation(libs.junit.ext)
  testImplementation(libs.androidX.test.core)
  testImplementation(libs.androidX.test.junit)
  testImplementation(libs.androidX.test.runner)
  testImplementation(libs.robolectric)

}
