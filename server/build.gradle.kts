import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
    id("com.github.gmazzo.buildconfig") version "5.5.0"
}

val apiKey: String = gradleLocalProperties(rootDir).getProperty("apiKey")

buildConfig {
    buildConfigField("API_KEY", apiKey)
}

group = "io.github.feliperce.cryptonews"
version = "1.0.0"
application {
    mainClass.set("io.github.feliperce.cryptonews.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cors)
    testImplementation(libs.kotlin.test.junit)

    // client
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.client.cio)
}