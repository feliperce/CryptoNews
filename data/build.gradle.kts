import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

val apiKey: String = gradleLocalProperties(rootDir).getProperty("apiKey") ?: "EMPTY_KEY"
val endpointDebugUrl = "https://newsapi.org/v2/"
val endpointReleaseUrl = "https://newsapi.org/v2/"

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk

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

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.Androidx.core)
    implementation(Dependencies.Androidx.appcompat)

    // Test
    testImplementation(Dependencies.Junit.junit)
    androidTestImplementation(Dependencies.Androidx.junit)

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

    // Koin
    implementation(Dependencies.Koin.koin)

    // Mockk
    testImplementation(Dependencies.Mockk.mockk)
}