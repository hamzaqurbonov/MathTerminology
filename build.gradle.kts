buildscript {
    dependencies {
        classpath(libs.google.services)
//        classpath ("com.android.tools.build:gradle:8.4.1")
        classpath ("com.google.gms:google-services:4.4.1")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false

//    id("com.google.gms.google-services") version "4.4.1" apply false
}