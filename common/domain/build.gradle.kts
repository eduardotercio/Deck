plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.example.common.domain"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
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
}

dependencies {

    // koin
    api(libs.koin.android)
    api(libs.koin.core)
    api(libs.koin.ktor)
    api(libs.koin.compose)
    api(libs.koin.androidx.compose)

    // coroutines
    api(libs.kotlinx.coroutines.core)

    // Serialization
    api(libs.kotlinx.serialization.json)

    // Tests
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockK.test)
    testImplementation(libs.ktor.test)
    testImplementation(libs.turbine.test)
    testImplementation(libs.junit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}