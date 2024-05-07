import shadow.bundletool.com.android.tools.r8.internal.T

plugins {
    id("com.android.application")
}

android {
    namespace = "com.uco.ucodgt"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.uco.ucodgt"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation("com.paypal.checkout:android-sdk:0.112.2")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}