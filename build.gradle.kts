// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.library") apply false
    id("com.android.application") apply false
    kotlin("android") apply false
}

subprojects {
    apply(plugin = "detekt-convention")
}
