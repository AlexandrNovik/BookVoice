import com.android.build.gradle.BaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

android {
    val libs: VersionCatalog =
        extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
    val sdkMinVersion = libs.findVersion("sdk-min").get().requiredVersion.toInt()
    val sdkTargetVersion = libs.findVersion("sdk-target").get().requiredVersion.toInt()

    compileSdkVersion(sdkTargetVersion)
    defaultConfig {
        multiDexEnabled = true
        minSdk = sdkMinVersion
        targetSdk = sdkTargetVersion
        versionCode = libs.findVersion("versionCode").get().requiredVersion.toInt()
        versionName = libs.findVersion("versionName").get().requiredVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        setProguardFiles(
            listOf(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard.pro"
            )
        )
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
        val optIns = listOf(
            "kotlin.RequiresOptIn",
            "kotlin.ExperimentalStdlibApi",
            "kotlin.contracts.ExperimentalContracts",
            "kotlin.time.ExperimentalTime",
            "kotlinx.coroutines.ExperimentalCoroutinesApi",
            "kotlinx.coroutines.FlowPreview",
        )
        freeCompilerArgs =
            (freeCompilerArgs + listOf("-progressive") + optIns.map { "-opt-in=$it" })
    }
}

fun Project.android(configure: BaseExtension.() -> Unit) {
    extensions.configure("android", configure)
}
