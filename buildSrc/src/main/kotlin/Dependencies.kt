object Dependencies {

    object Androidx {
        const val core = "androidx.core:core-ktx:${Versions.coreKtx}"
        const val junit = "androidx.test.ext:junit:${Versions.androidxJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
        const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"

        object Compose {
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
            const val activity = "androidx.activity:activity-compose:${Versions.activityCompose}"
            const val uiTestJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        }

        object Lifecycle {
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        }

        object Room {
            const val runtime = "androidx.room:room-runtime:${Versions.room}"
            const val compiler = "androidx.room:room-compiler:${Versions.room}"
            const val ktx = "androidx.room:room-ktx:${Versions.room}"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Junit {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object Square {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val okHttpBom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttp}"
        const val okHttp = "com.squareup.okhttp3:okhttp"
        const val okHttpLogging = "com.squareup.okhttp3:logging-interceptor"
    }

}