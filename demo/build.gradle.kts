import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    id("io.github.taz03.compose.web.navigator")
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets.commonMain.dependencies {
        implementation(libs.compose.runtime)
        implementation(libs.compose.foundation)
        implementation(libs.compose.ui)

        implementation(libs.compose.material3)

        implementation("io.github.taz03:compose-web-navigator:1.1.0")
    }
}
