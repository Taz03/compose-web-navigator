package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import kotlinx.browser.window

@Composable
fun NavHost(
    navController: NavController,
    defaultTitle: String,
    builder: NavGraph.() -> Unit
) {
    val navGraph = remember { NavGraph(defaultTitle).apply(builder) }

    SideEffect {
        navController.currentRoute = navGraph.getRoute(
            location = window.location.pathname,
            search = window.location.search
        )

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
