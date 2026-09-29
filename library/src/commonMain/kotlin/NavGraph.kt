package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.Composable

class NavGraph {
    internal constructor()

    private val routes = mutableMapOf<String, @Composable (Route) -> Unit>()
    private val routeMatcher = mutableMapOf<String, String>()

    private lateinit var `404`: @Composable (Route) -> Unit

    fun route(
        path: String,
        content: @Composable (Route) -> Unit
    ) {
        routes[path] = content

        routeMatcher[
            path.trimEnd('/').replace("\\{([^/]+)\\}".toRegex()) {
                "(?<${it.groupValues[1]}>[^/]+)"
            }
        ] = path
    }

    fun `404`(content: @Composable (Route) -> Unit) {
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
                pathParameters = "\\{([^/]+)\\}".toRegex()
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
        val content = routes[route.path] ?: `404`
        content(route)
    }
}
