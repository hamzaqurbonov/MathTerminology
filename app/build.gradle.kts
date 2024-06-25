plugins {
    alias(libs.plugins.androidApplication)
    id("com.google.gms.google-services")

//    id("com.android.application")
//    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.mathterminology"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mathterminology"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    

    implementation ("com.firebaseui:firebase-ui-database:8.0.2")
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.orhanobut:dialogplus:1.11@aar")

    //DocumentSnapshot
      implementation ("com.google.firebase:firebase-firestore:17.0.4")

    // onRefresh()
    implementation ("androidx.recyclerview:recyclerview:1.1.0")
    implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

}