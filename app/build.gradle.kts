@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.playServices)

    // Ensure you have the Compose plugin applied and enabled correctly
    id("org.jetbrains.compose") version "1.5.11" apply false  // Compose Plugin for Kotlin 2.0+
    // Ensure you also apply the Compose Compiler plugin when needed
}

android {
    namespace = "com.apps.aivision"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.apps.aivision"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11" // Ensure this matches your Compose version
    }

    kapt {
        correctErrorTypes = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    bundle {
        language {
            enableSplit = false
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom)) // BOM for Compose
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.material.icons)
    implementation(libs.appcompat)
    // navigation
    implementation(libs.navigation.compose)
    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.navigation.hilt)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    // JSON Parsing
    implementation(libs.google.gson)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.convertor)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.play.service)

    // ViewModel
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.activity.compose.ktx)

    // LiveData
    implementation(libs.lifecycle.livedata)

    implementation(libs.ritchtext.common)
    implementation(libs.ritchtext.ui.material)
    implementation(libs.ritchtext.ui.material3)

    // In-App Purchase
    implementation(libs.billing.client)
    implementation(libs.billing.client.ktx)

    // AdMob
    implementation(libs.play.services.admob)
    implementation(libs.play.services.admob.lite)
    // Glide
    implementation(libs.glide)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth) {
        exclude("com.google.android.gms", "play-services-safetynet")
    }
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)

    // Splash
    implementation(libs.splash.api)

    // PDF
    implementation(libs.itext.android)

    // Root detection
    implementation(libs.rootbeer.lib)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
