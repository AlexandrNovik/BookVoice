plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    maven {
        url = uri("https://plugins.gradle.org/m2/")
    }
}

gradlePlugin {
    plugins {
        create("compose") {
            id = "voice.compose"
            implementationClass = "ComposePlugin"
        }
        create("app") {
            id = "voice.app"
            implementationClass = "AppPlugin"
        }
    }
}

dependencies {
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.0")
    implementation("com.android.tools.build:gradle:8.0.2")
    implementation(kotlin("gradle-plugin", version = "1.8.21"))
}