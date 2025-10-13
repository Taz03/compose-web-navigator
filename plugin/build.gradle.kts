import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradle.plugin.publish)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

group = "io.github.taz03"
version = libs.versions.version.get()

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.status.pages)
}

tasks.compileKotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_1_8) }
}

gradlePlugin {
    website = "https://github.com/Taz03/compose-web-navigator"
    vcsUrl = "https://github.com/Taz03/compose-web-navigator.git"

    plugins {
        create("composeWebNavigator") {
            id = "io.github.taz03.compose.web.navigator"
            displayName = "Compose Web Navigator Plugin"
            description = "Builds and Runs composes app using a local web server."
            implementationClass = "io.github.taz03.compose.web.navigator.ComposeWeb"
            tags = listOf("kotlin", "compose", "web", "navigator", "jetpack", "spanner")
        }
    }
}
