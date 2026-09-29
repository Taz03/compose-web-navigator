package io.github.taz03.compose.web.navigator.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.taz03.compose.web.navigator.NavController
import io.github.taz03.compose.web.navigator.NavHost
import io.github.taz03.compose.web.navigator.Route

@Composable
fun App() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    val navController = rememberSaveable { NavController() }

    Text("Hello, Web!\n\n")

    NavHost(navController = navController) {
        route("/") {
            Text("Home")
        }
        route("/about") {
            Text("About")

            Button({
                Route(
                    path = "/user/{id}",
                    pathParameters = mapOf("id" to "taz"),
                    args = mapOf("status" to 123)
                ).let(navController::navigate)
            }) {
                Text("Taz")
            }
        }
        route("/user/{id}") { route ->
            Text("User ID: ${route.pathParameters["id"]}")
            Text("Query: ${route.queryParameters["query"]}")
            Text("Args: ${route.args}")
        }

        `404` {
            Text("404 Not found")
        }
    }
}
