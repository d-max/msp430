plugins {
    id("com.android.application") apply true
    kotlin("android")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4")
    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.annotation:annotation-experimental:1.0.0")
}

android {

    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        applicationId = "dmax.scara.android"
        versionCode = 1
        versionName = "0.1"

        vectorDrawables.useSupportLibrary = true

    }


    sourceSets["main"].java.srcDir("src/main/kotlin")
}
