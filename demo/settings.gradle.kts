pluginManagement {
    includeBuild("..")
    repositories {
        maven { url = uri("../build/localMaven") }
        mavenLocal()
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    includeBuild("..")
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}
