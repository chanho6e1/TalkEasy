plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ssafy.talkeasy.feature.aac"
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    buildFeatures {
        compose = true
    }
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

    implementation(project(":core:domain"))
    implementation(project(":feature:common"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:follow"))
    implementation(project(":feature:location"))
}

kapt {
    correctErrorTypes = true
}