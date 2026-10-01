package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.browser.document

class NavGraph internal constructor(private val defaultTitle: String) {
    private val routeMatcher = mutableMapOf<String, String>()

    private val titles = mutableMapOf<String, (Route) -> String>()
    private val contents = mutableMapOf<String, @Composable (Route) -> Unit>()

    private var `404Title`: ((Route) -> String)? = null
    private lateinit var `404`: @Composable (Route) -> Unit

    fun route(
        path: String,
        titleBuilder: ((Route) -> String)? = null,
        content: @Composable (Route) -> Unit
    ) {
        routeMatcher[
            path.trimEnd('/').replace(":([^/]+)".toRegex()) {
                "(?<${it.groupValues[1]}>[^/]+)"
            }
        ] = path

        contents[path] = content
        titleBuilder?.let { titles[path] = it }
    }

    fun `404`(
        titleBuilder: ((Route) -> String)? = null,
        content: @Composable (Route) -> Unit
    ) {
        `404Title` = titleBuilder
        `404` = content
    }

    internal fun getRoute(
        location: String,
        search: String
    ): Route {
        routeMatcher.forEach { (routeRegex, rawRoute) ->
            val matchResult = routeRegex.toRegex().matchEntire(location.trimEnd('/'))

            if (matchResult != null) return Route(
                path = rawRoute,
                pathParameters = ":([^/]+)".toRegex()
                    .findAll(rawRoute)
                    .map { it.groupValues[1] }
                    .associateWith { matchResult.groups[it]?.value.orEmpty() },
                queryParameters = search.trimStart('?')
                    .split("&")
                    .filter(String::isNotEmpty)
                    .associate {
                        val (key, value) = it.split("=")
                        key to value
                    }
            )
        }

        return Route(path = location)
    }

    @Composable
    internal fun Content(route: Route) {
        LaunchedEffect(route) {
            val title = (if (contents.containsKey(route.path)) titles[route.path] else `404Title`) ?: { defaultTitle }
            document.title = title(route)
        }

        val content = contents[route.path] ?: `404`
        content(route)
    }
}
