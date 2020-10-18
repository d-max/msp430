plugins {
    id("com.android.application") apply true
    kotlin("android")
}

android {

    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)

        applicationId = "dmax.scara.android"
        versionName = "0.1"
        versionCode = 1

        vectorDrawables.useSupportLibrary = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")

    implementation("androidx.annotation:annotation:1.1.0")
    implementation("androidx.annotation:annotation-experimental:1.0.0")
    implementation("androidx.fragment:fragment:1.2.5")

    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0-alpha01")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.google.android.material:material:1.3.0-alpha03")

    implementation("org.koin:koin-core:2.2.0-rc-3")
    implementation("org.koin:koin-core-ext:2.2.0-rc-3")
    implementation("org.koin:koin-android:2.2.0-rc-3")
    implementation("org.koin:koin-android-viewmodel:2.2.0-rc-3")
}
