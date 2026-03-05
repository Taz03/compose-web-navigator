package io.github.taz03.compose.web.navigator.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import io.github.taz03.compose.web.navigator.NavController
import io.github.taz03.compose.web.navigator.NavHost
import io.github.taz03.compose.web.navigator.Route

@Composable
fun App() = Column {
    Text("Hello, World!")
    val navController = rememberSaveable { NavController() }

    NavHost(navController = navController) {
        route("/") {
            Text("Home")
        }
        route("/about") {
            Text("About")

            Button({ navController.navigate(Route(path = "/user/{id}", pathParameters = mapOf("id" to "taz"))) }) {
                Text("Taz")
            }
        }
        route("/user/{id}") { route ->
            Text("User ID: ${route.pathParameters["id"]}")
            Text("Query: ${route.queryParameters["query"]}")
        }
    }
}
