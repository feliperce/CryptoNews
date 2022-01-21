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

    implementation(Dependencies.Androidx.appcompat)

    // Test
    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)

    // s2 Compose s2
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.activity)
    androidTestImplementation(Dependencies.Androidx.Compose.uiTestJunit)
    implementation(Dependencies.Androidx.Compose.navHost)

    // Koin
    implementation(Dependencies.Koin.koin)

    implementation(project(":data"))
    implementation(project(":home"))
    implementation(project(":design"))
}