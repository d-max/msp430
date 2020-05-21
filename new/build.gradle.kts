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
    implementation("androidx.fragment:fragment:1.2.4")

    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta6")
    implementation("androidx.viewpager2:viewpager2:1.1.0-alpha01")
    implementation("androidx.cardview:cardview:1.0.0")

    implementation("com.google.android.material:material:1.2.0-alpha06")

    implementation("org.koin:koin-core:2.1.5")
    implementation("org.koin:koin-core-ext:2.1.5")
    implementation("org.koin:koin-android:2.1.5")
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
