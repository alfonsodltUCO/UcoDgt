import shadow.bundletool.com.android.tools.r8.internal.T

plugins {
    id("com.android.application")
}

android {
    namespace = "com.uco.ucodgt"
    compileSdk = 34
    //aaptOptions.cruncherEnabled = false
    //aaptOptions.useNewCruncher = false
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

    /*implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\Lenovo\\AppData\\Local\\Android\\Sdk\\platforms\\android-34",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf<T>()
    )))*/
    implementation("com.github.1902shubh:SendMail:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    implementation("com.paypal.checkout:android-sdk:0.112.2")
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.22")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}