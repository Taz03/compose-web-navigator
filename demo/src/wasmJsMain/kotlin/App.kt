package io.github.taz03.composewebnavigator.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import io.github.taz03.compose.web.navigator.NavController
import io.github.taz03.compose.web.navigator.NavHost

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
        }
        route("/user/{id}") { route ->
            Text("User ID: ${route.pathParameters["id"]}")
            Text("Query: ${route.queryParameters["query"]}")
        }
    }
}
