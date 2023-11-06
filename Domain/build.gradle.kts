plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.tc.hamie.chakib"
    compileSdk = 34

    defaultConfig {
        //applicationId = "com.tc.hamie.chakib"
        minSdk = 26
        targetSdk = 33
        //versionCode = 1
        //versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation(project(":common"))
    implementation("androidx.room:room-common:2.6.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.room:room-runtime:2.5.0")
    annotationProcessor("androidx.room:room-compiler:2.5.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.1")
    implementation("androidx.room:room-ktx:2.5.0")
    implementation("androidx.room:room-rxjava2:2.5.0")
    implementation("androidx.room:room-rxjava3:2.5.0")
    implementation("androidx.room:room-paging:2.5.0")
    implementation("androidx.room:room-guava:2.5.0")
    testImplementation("androidx.room:room-testing:2.5.0")
}