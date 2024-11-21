plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "hu.szoftarch.webshop"
    compileSdk = 34

    defaultConfig {
        applicationId = "hu.szoftarch.webshop"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core Android libraries
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)

    // AndroidX Navigation & Material
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)

    // Coil for image loading
    implementation(libs.coil.compose)

    // Hilt for Dependency Injection
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)

    // Retrofit & Moshi for networking and JSON parsing
    implementation(libs.converter.gson)
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.retrofit)

    // ML Kit for text recognition
    implementation(libs.play.services.mlkit.text.recognition)
    implementation(libs.play.services.mlkit.text.recognition.common)
    implementation(libs.vision.common)

    // Jetpack Compose Testing
    implementation(libs.androidx.ui.test.android)

    // Test dependencies
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.junit)

    // Debugging tools
    debugImplementation(libs.androidx.ui.test.manifest)
    debugImplementation(libs.androidx.ui.tooling)

    // Kotlin Coroutines with Play Services
    implementation(libs.jetbrains.kotlinx.coroutines.play.services)

    // Kapt (annotation processors)
    implementation(libs.logging.interceptor)
    kapt(libs.hilt.android.compiler)

    //lifecycle
    implementation(libs.androidx.lifecycle.runtime.compose)

}

kapt {
    correctErrorTypes = true
}
