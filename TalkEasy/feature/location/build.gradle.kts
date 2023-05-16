plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.ssafy.talkeasy.feature.location"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(libs.bundles.google.map)

    implementation(libs.stomp)

    implementation(project(":core:domain"))
    implementation(project(":feature:common"))
}

kapt {
    correctErrorTypes = true
}