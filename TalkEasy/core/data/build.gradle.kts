plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ssafy.talkeasy.core.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        targetSdk = 33
    }
}

dependencies {
    implementation(libs.androidx.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp3)

    implementation(project(":core:domain"))
}