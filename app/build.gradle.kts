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
        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "org.junit.runners.JUnit4"
        // Use JUnit 5 for local unit tests
        testInstrumentationRunnerArguments["runnerBuilder"] = trueStr
        testInstrumentationRunnerArguments["de.mannodermaus.junit5.AndroidJUnit5Builder"] = trueStr
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
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
    composeCompiler {
//        includeSourceInformation = true
//        includeTraceMarkers = true
    }
//    composeOptions {
//
//    }
    // buildFeatures.compose = true is no longer necessary in Kotlin 2.0.0 and above
    /*
    buildFeatures {
        compose = true
    }
    */
    buildToolsVersion = "35.0.0"
}

// Needed to run tests with JUnit5
tasks.withType<Test> {
    useJUnitPlatform() // Make all tests use JUnit 5
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

    // Koin DI
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-android
    runtimeOnly(libs.kotlinx.coroutines.android)

    // Androidx Startup
    implementation(libs.androidx.startup.runtime)

    // Possible solution to enable JUnit5 seems to be ok when commenting out the following
    // after adding the 'tasks.withType<Test>' block above.  Keeping this just for reference
    // https://stackoverflow.com/a/64575152/3980474
    //    testImplementation(kotlin("test-junit5"))
    //    testImplementation(platform("org.junit:junit-bom:5.11.3"))

    // Use kotlin testing
    // https://kotlinlang.org/docs/jvm-test-using-junit.html#add-dependencies
//    testImplementation(kotlin("test"))

    testImplementation(libs.junit.five.api)
    testImplementation(libs.junit.five.engine)
    testImplementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}