package io.github.taz03.compose.web.navigator

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import kotlin.jvm.java

abstract class ComposeWeb : Plugin<Project> {
    override fun apply(project: Project) {
        val requiredPlugins = listOf(
            "org.jetbrains.kotlin.multiplatform",
            "org.jetbrains.kotlin.plugin.compose",
            "org.jetbrains.compose"
        )

        check(requiredPlugins.all(project.plugins::hasPlugin)) {
            "The following plugins must be applied: $requiredPlugins"
        }

        val buildDir = project.layout.buildDirectory.asFile.get()

        project.tasks.register(
            "runWebServer",
            RunWebServerTask::class.java,
            File(buildDir, "dist/wasmJs/productionExecutable")
        ).configure {
            it.group = "Compose Web Navigator"
            it.description = "Runs a local web server to serve the Compose for Web application."

            it.dependsOn("wasmJsBrowserDistribution")
        }
    }
}
