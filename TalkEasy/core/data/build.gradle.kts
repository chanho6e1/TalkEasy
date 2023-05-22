plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
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

    implementation(libs.rabbitmq)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(project(":core:domain"))
    implementation(project(":feature:common"))
}

kapt {
    correctErrorTypes = true
}