package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.browser.window

class NavController {
    private var location by mutableStateOf(window.location.pathname)
    private var params by mutableStateOf(window.location.search)

    var currentRoute by mutableStateOf<Route?>(null)
        internal set

    fun navigate(route: Route) {
        currentRoute = route
        window.history.pushState(null, "", route.url)
    }

    fun back() = window.history.back()
    fun forward() = window.history.forward()
}
