package io.github.taz03.compose.web.navigator

import androidx.compose.runtime.Composable

class NavGraph {
    internal constructor()

    private val routes = mutableMapOf<String, @Composable (Route) -> Unit>()
    private val routeMatcher = mutableMapOf<String, String>()

    fun route(
        path: String,
        content: @Composable (Route) -> Unit
    ) {
        routes[path] = content

        routeMatcher[
            path.replace("\\{[^/]+}".toRegex()) {
                "(<${it.groupValues[1]}>[^/]+)"
            }
        ] = path
    }

    internal fun getRoute(
        location: String,
        search: String
    ): Route {
        routeMatcher.forEach { (routeRegex, rawRoute) ->
            val regex = routeRegex.toRegex()
            val matchResult = regex.matchEntire(location.trimEnd('/'))

            if (matchResult != null) {
                val pathParameters = "\\{([^/]+)}".toRegex()
                    .findAll(rawRoute)
                    .map { it.groupValues[1] }
                    .associateWith { matchResult.groups[it]?.value.orEmpty() }

                val queryParameters = search.trimStart('?')
                    .split("&")
                    .associate {
                        val (key, value) = it.split("=")
                        Pair(key, value)
                    }

                return Route(
                    path = rawRoute,
                    pathParameters = pathParameters,
                    queryParameters = queryParameters.toMap()
                )
            }
        }

        return Route(path = location)
    }

    @Composable
    internal fun Content(route: Route) {
        val content = routes[route.path] ?: return
        content(route)
    }
}
