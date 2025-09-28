plugins {
    `kotlin-dsl`
    alias(libs.plugins.gradle.plugin.publish)
}

group = "io.github.taz03"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.server.status.pages)
}

gradlePlugin {
    website.set("https://github.com/Taz03/compose-web-navigator")
    vcsUrl.set("https://github.com/Taz03/compose-web-navigator.git")
    plugins {
        create("runWebServer") {
            id = "io.github.taz03.compose.web.navigator"
            implementationClass = "io.github.taz03.compose.web.navigator.RunWebServer"
        }
    }
}
