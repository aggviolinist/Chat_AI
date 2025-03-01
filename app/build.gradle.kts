@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.daggerHilt)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.playServices)
    alias(libs.plugins.compose.compiler)


    // JetBrains Compose Plugin (For Compose UI)
}

android {
    namespace = "com.apps.imageAI"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.apps.imageAI"
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
        kotlinCompilerExtensionVersion = "1.5.11" // Ensure it matches Compose BOM
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
    implementation("androidx.credentials:credentials:1.2.0")


    // Google Guava for Android
    implementation(libs.guava)

    // Navigation
    implementation(libs.navigation.compose)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.googleid)
    kapt(libs.hilt.compiler)
    implementation(libs.navigation.hilt)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

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

    // ViewModel & LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.activity.compose.ktx)

    // Rich Text UI
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

    // Credential Manager
//    implementation(libs.credential.manager)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth) {
        exclude("com.google.android.gms", "play-services-safetynet")
    }
    implementation(libs.firebase.firestore)
    implementation(libs.play.services.auth)

    // Splash API
    implementation(libs.splash.api)

    // PDF
    implementation(libs.itext.android)

    // Root detection
    implementation(libs.rootbeer.lib)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

}
