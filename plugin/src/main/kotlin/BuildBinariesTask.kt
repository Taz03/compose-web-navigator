package io.github.taz03.compose.web.navigator

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

abstract class BuildBinariesTask @Inject constructor(
    private val buildDir: File
) : DefaultTask() {
    @TaskAction
    fun action() {
        val appDir = File(buildDir, "app")
        val wasmDir = File(buildDir, "kotlin-webpack/wasmJs/productionExecutable")
        val resourcesDir = File(buildDir, "processedResources/wasmJs/main")

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
