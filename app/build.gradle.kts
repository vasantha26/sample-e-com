import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")

}

android {
    namespace = "com.example.sample"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sample"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    android {
        (this as ExtensionAware).configure<KotlinJvmOptions> {
            jvmTarget = "1.8"
        }
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    // Dagger Hilt for dependency injection
    implementation(libs.hilt.android.v248)
    kapt(libs.hilt.compiler.v248)

    implementation(libs.androidx.paging.runtime.ktx)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.glide)

    implementation(libs.androidx.navigation.fragment.ktx.v270)
    implementation (libs.androidx.navigation.ui.ktx.v270)
    implementation (libs.material.v190)
    implementation (libs.androidx.drawerlayout)

    // Coroutines (for Paging and API calls)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
}