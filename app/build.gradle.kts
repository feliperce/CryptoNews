import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

val apiKey: String = gradleLocalProperties(rootDir).getProperty("apiKey") ?: "EMPTY_KEY"
val endpointDebugUrl = "https://newsapi.org/v2/"
val endpointReleaseUrl = "https://newsapi.org/v2/"

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "br.com.mobileti.cryptonews"
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "ENDPOINT_URL", "\"$endpointReleaseUrl\"")
        }
        getByName("debug") {
            buildConfigField("String", "ENDPOINT_URL", "\"$endpointDebugUrl\"")
        }
    }
    compileOptions {
        sourceCompatibility(1.8)
        targetCompatibility(1.8)
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.appcompat)
    implementation(Dependencies.Google.material)

    // Lifecycle
    implementation(Dependencies.Androidx.Lifecycle.runtimeKtx)

    // Test
    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)
    androidTestImplementation(Dependencies.Androidx.espresso)

    // s2 Compose s2
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.toolingPreview)
    implementation(Dependencies.Androidx.Compose.activity)
    androidTestImplementation(Dependencies.Androidx.Compose.uiTestJunit)
    debugImplementation(Dependencies.Androidx.Compose.tooling)
    // Accompanist
    implementation(Dependencies.Google.Accompanist.swipRefresh)

    // Room
    implementation(Dependencies.Androidx.Room.runtime)
    kapt(Dependencies.Androidx.Room.compiler)
    implementation(Dependencies.Androidx.Room.ktx)

    // Retrofit
    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.gsonConverter)
    // okhttp
    implementation(platform(Dependencies.Square.okHttpBom))
    implementation(Dependencies.Square.okHttp)
    implementation(Dependencies.Square.okHttpLogging)

    // Coroutines
    implementation(Dependencies.Jetbrains.coroutines)

    // Koil
    implementation(Dependencies.Coil.coil)
    implementation(Dependencies.Coil.coilCompose)

    // Koin
    implementation(Dependencies.Koin.koin)
    implementation(Dependencies.Koin.workmanager)
    implementation(Dependencies.Koin.compose)
}