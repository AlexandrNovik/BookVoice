include(":app")
include(":libraries:core")
include(":libraries:test")
include(":libraries:splitInstall")
include(":libraries:designsystem")
include(":libraries:navigation")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        includeBuild("plugins")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
