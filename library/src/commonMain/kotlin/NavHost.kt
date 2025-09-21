package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.browser.window

@Composable
fun NavHost(
    navController: NavController,
    builder: NavGraph.() -> Unit
) {
    val navGraph = NavGraph().apply(builder)

    LaunchedEffect(Unit) {
        navController.currentRoute = navGraph.getRoute(
            location = window.location.pathname,
            search = window.location.search
        )

        print(navController.currentRoute)

        window.addEventListener("popstate") {
            navController.navigate(
                route = navGraph.getRoute(
                    location = window.location.pathname,
                    search = window.location.search
                )
            )
        }
    }

    navController.currentRoute?.let {
        navGraph.Content(it)
    }
}
