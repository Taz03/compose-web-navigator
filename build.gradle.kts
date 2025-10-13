plugins {
    alias(libs.plugins.kotlin.multiplatform) apply  false
    alias(libs.plugins.kotlin.jvm) apply  false
    alias(libs.plugins.vanniktech.mavenPublish) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
}
