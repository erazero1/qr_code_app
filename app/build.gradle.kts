plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization") version "2.1.20"
    id("com.google.devtools.ksp") version "2.1.20-1.0.31"
    id("com.google.dagger.hilt.android") version "2.56.1"
}

android {
    namespace = "com.erazero1.qrcodeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.erazero1.qrcodeapp"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    // Code scanner
    implementation (libs.play.services.code.scanner)
    // QR Code generator
    implementation(libs.qrcode.kotlin)

    // Hilt
    implementation (libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp (libs.hilt.compiler)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.navigation.compose)
    // Coil
    implementation("io.coil-kt.coil3:coil-compose:3.1.0")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}