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
            path.trimEnd('/').replace("\\{([^/]+)\\}".toRegex()) {
                "(?<${it.groupValues[1]}>[^/]+)"
            }
        ] = path
    }

    internal fun getRoute(
        location: String,
        search: String
    ): Route {
        routeMatcher.forEach { (routeRegex, rawRoute) ->
            print("$routeRegex, $rawRoute")
            val regex = routeRegex.toRegex()
            val matchResult = regex.matchEntire(location.trimEnd('/'))

            if (matchResult != null) {
                print("match")
                val pathParameters = "\\{([^/]+)\\}".toRegex()
                    .findAll(rawRoute)
                    .map { it.groupValues[1] }
                    .associateWith { matchResult.groups[it]?.value.orEmpty() }
                print("pathParameters: $pathParameters")

                print("search: $search")
                val queryParameters = search.trimStart('?')
                    .split("&")
                    .filter(String::isNotEmpty)
                    .associate {
                        val (key, value) = it.split("=")
                        Pair(key, value)
                    }
                print("queryParameters: $queryParameters")

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
