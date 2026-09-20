import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.vanniktechMavenPublish)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

group = "io.github.taz03"
version = libs.versions.version.get()

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "compose-web-navigator", version.toString())

    pom {
        name = "Compose Web Navigator"
        description = "This library provides androidx navigation-like API for Jetpack Compose for Web SPA like navigation."
        inceptionYear = "2025"
        url = "https://github.com/Taz03/compose-web-navigator"
        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }
        developers {
            developer {
                id = "Taz03"
                name = "Tanish Azad"
                url = "https://github.com/Taz03"
            }
        }
        scm {
            url = "https://github.com/Taz03/compose-web-navigator"
            connection = "scm:git:git://github.com/Taz03/compose-web-navigator.git"
            developerConnection = "scm:git:ssh://git@github.com/Taz03/compose-web-navigator.git"
        }
    }
}
