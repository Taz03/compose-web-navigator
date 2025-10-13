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
version = "0.1.0"

publishing {
    repositories {
        mavenLocal()
    }
}

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
tasks.compileTestKotlin {
    compilerOptions { jvmTarget.set(JvmTarget.JVM_1_8) }
}

gradlePlugin {
    website.set("https://github.com/Taz03/compose-web-navigator")
    vcsUrl.set("https://github.com/Taz03/compose-web-navigator.git")
    plugins {
        create("composeWeb") {
            id = "io.github.taz03.compose.web.navigator"
            implementationClass = "io.github.taz03.compose.web.navigator.ComposeWeb"
        }
    }
}
