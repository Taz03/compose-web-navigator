package io.github.taz03.compose.web.navigator

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import java.io.File

abstract class BuildBinariesTask : DefaultTask() {
    @TaskAction
    fun action() {
        val appDir = File(project.layout.buildDirectory.asFile.get(), "app")
        val wasmDir = File(project.layout.buildDirectory.asFile.get(), "kotlin-webpack/wasmJs/productionExecutable")
        val resourcesDir = File(project.layout.buildDirectory.asFile.get(), "processedResources/wasmJs/main")

        appDir.mkdirs()

        fun moveAllFiles(sourceDir: File, destinationDir: File) {
            if (!sourceDir.exists() || !sourceDir.isDirectory) {
                println("Source directory not found: ${sourceDir.path}")
                return
            }

            sourceDir.listFiles()?.forEach { file ->
                val destinationFile = File(destinationDir, file.name)

                if (file.renameTo(destinationFile)) println("Moved: ${file.name} -> ${destinationDir.name}/")
                else println("Failed to move: ${file.name}")
            }
        }

        moveAllFiles(wasmDir, appDir)
        moveAllFiles(resourcesDir, appDir)
    }
}

class RunWebServerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("buildBinaries", BuildBinariesTask::class.java) {
            group = "application"
            description = "Runs a simple web server to serve the static files."

            dependsOn("wasmJsBrowserProductionWebpack")
        }

        embeddedServer(CIO, port = 8080) {
            install(StatusPages) {
                status(HttpStatusCode.NotFound) { call, status ->
                    val file = File(project.layout.buildDirectory.asFile.get(), "app/index.html")
                    call.respondText(file.readText(), ContentType.Text.Html, status)
                }
            }

            routing {
                staticFiles("/", File(project.layout.buildDirectory.asFile.get(), "app")) {
                    default("index.html")
                }
            }
        }.start(true)
    }
}
