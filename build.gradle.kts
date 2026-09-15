buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.androidx.navigation.safeargs.gradle.plugin)
    }
}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.navigation.safeargs) apply false
}
