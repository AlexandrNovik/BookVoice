// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    kotlin("android") version "1.8.20" apply false
}

subprojects {
    apply(plugin = "detekt-convention")
}