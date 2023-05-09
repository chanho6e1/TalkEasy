plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ssafy.talkeasy.feature.common"

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }
    compileSdk = 33
}

dependencies {
    implementation(libs.bundles.androidx)
    testImplementation(libs.junit)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.bundles.compose)

    androidTestImplementation(libs.androidx.compose.ui.test.junit)
    debugImplementation(libs.bundles.debug.compose)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.glide.compose)

    implementation(libs.lottie.compose)

    implementation(project(":core:domain"))
}

kapt {
    correctErrorTypes = true
}