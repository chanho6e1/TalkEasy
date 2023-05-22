plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.ssafy.talkeasy.core.network"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.bundles.androidx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp3)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(project(":core:domain"))
    implementation(project(":core:data"))
}

kapt {
    correctErrorTypes = true
}