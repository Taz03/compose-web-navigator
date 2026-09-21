package io.github.taz03.compose.web.navigator

import org.gradle.api.Plugin
import org.gradle.api.Project
import kotlin.jvm.java

abstract class ComposeWeb : Plugin<Project> {
    override fun apply(project: Project) {
        val requiredPlugins = listOf("org.jetbrains.kotlin.multiplatform", "org.jetbrains.compose", "org.jetbrains.kotlin.plugin.compose")
        if (!requiredPlugins.all { project.plugins.hasPlugin(it) }) {
            throw IllegalStateException("ComposeWeb plugin requires the following plugins to be applied: $requiredPlugins")
        }

        val buildDir = project.layout.buildDirectory.asFile.get()

        project.tasks.register("runWebServer", RunWebServerTask::class.java, buildDir).configure {
            it.group = "Compose Web Navigator"
            it.description = "Runs a local web server to serve the Compose for Web application."

            it.dependsOn("wasmJsBrowserDistribution")
        }
    }
}
