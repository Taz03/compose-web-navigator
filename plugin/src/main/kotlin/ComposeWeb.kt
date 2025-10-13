package io.github.taz03.compose.web.navigator

import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class ComposeWeb : Plugin<Project> {
    override fun apply(project: Project) {
        val buildDir = project.layout.buildDirectory.asFile.get()

        project.tasks.register("buildWebBinaries", BuildBinariesTask::class.java, buildDir).configure {
            it.group = "compose-web-navigator"
            it.description = "Builds the WebAssembly binaries and moves them to the app directory."

            it.dependsOn("wasmJsBrowserProductionWebpack")
        }

        project.tasks.register("runWebServer", RunWebServerTask::class.java, buildDir).configure {
            it.group = "compose-web-navigator"
            it.description = "Runs a local web server to serve the Compose for Web application."

            it.dependsOn("buildWebBinaries")
        }
    }
}
