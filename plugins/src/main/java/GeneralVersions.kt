import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.jvm.toolchain.JavaLanguageVersion

val VersionCatalog.composeVersion: String
    get() = findVersion("compose_compiler").get().requiredVersion

object GeneralVersions {
    const val versionCode = 1
    const val versionName = "1.0.0"

    const val compileSdk = 33
    const val targetSdk = 33
    const val minSdk = 24
    val javaCompileVersion = JavaVersion.VERSION_17
    val javaLanguageVersion: JavaLanguageVersion = JavaLanguageVersion.of(17)
}
