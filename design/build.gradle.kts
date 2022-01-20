
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(Dependencies.Androidx.Compose.navHost)

}