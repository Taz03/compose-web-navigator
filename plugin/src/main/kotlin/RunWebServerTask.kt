package io.github.taz03.compose.web.navigator

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.TaskAction
import java.io.File
import javax.inject.Inject

@CacheableTask
abstract class RunWebServerTask @Inject constructor(
    private val distDir: File
) : DefaultTask() {
    @TaskAction
    fun action() = runBlocking {
        embeddedServer(CIO, port = 8080) {
            install(StatusPages) {
                status(HttpStatusCode.NotFound) { call, status ->
                    val file = File(distDir, "index.html")
                    call.respondText(file.readText(), ContentType.Text.Html, status)
                }
            }

            routing {
                staticFiles("/", distDir) {
                    default("index.html")
                }
            }

        }
            .also { println("Server started at http://localhost:8080") }
            .start(true)
            .stopSuspend()
    }
}
