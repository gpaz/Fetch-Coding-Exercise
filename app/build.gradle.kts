plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.algee.fetchexercise"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.algee.fetchexercise"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        val trueStr = "true"

        // Use JUnit 4 for instrumentation tests
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "org.junit.runners.JUnit4"
        // Use JUnit 5 for local unit tests
        testInstrumentationRunnerArguments["runnerBuilder"] = trueStr
        testInstrumentationRunnerArguments["de.mannodermaus.junit5.AndroidJUnit5Builder"] = trueStr
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    buildToolsVersion = "35.0.0"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation(libs.squareup.moshi)
    implementation(libs.squareup.moshi.kotlin)
    implementation(libs.squareup.moshi.adapters)
    implementation(libs.squareup.moshi.kotlin.codegen)
    implementation(libs.squareup.retrofit2)
    implementation(libs.squareup.retrofit2.converter.moshi)
    /**
     * Unused in the current implementation; opted to use moshi instead.
     * Just have it here for reference.
     */
    //implementation(libs.squareup.retrofit2.converter.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.okhttp.logginginterceptor)
    implementation(libs.kotlinx.lifecycle.viewmodel.ktx)
    implementation(libs.kotlinx.lifecycle.livedata)
    implementation(libs.kotlinx.lifecycle.livedata.ktx)
    implementation(libs.kotlinx.lifecycle.runtime)
    implementation(libs.kotlinx.lifecycle.runtime.ktx)

    // MichaelBull Result implementation
    implementation(libs.michaelbull.kotlinresult)
    implementation(libs.michaelbull.kotlinresult.coroutines)
//    implementation(libs.google.guava.android) // NOT NEEDED

    // Koin DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
    runtimeOnly(libs.kotlinx.coroutines.android)

    // Androidx Startup
    implementation(libs.androidx.startup.runtime)


    testImplementation(libs.junit.five.api)
    testImplementation(libs.junit.five.engine)
    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)



    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)


    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}