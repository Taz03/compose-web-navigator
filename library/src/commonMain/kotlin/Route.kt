package io.github.taz03.compose.web.navigator

data class Route(
    val path: String,
    val pathParameters: Map<String, String> = emptyMap(),
    val queryParameters: Map<String, String> = emptyMap(),
    val args: Map<String, Any> = emptyMap()
) {
    val pathname: String
        get() {
            var pathname = path
            pathParameters.forEach {
                pathname = pathname.replace(":${it.key}".toRegex(RegexOption.IGNORE_CASE), it.value)
            }

            return pathname
        }

    val url
        get() = buildString {
            append(pathname)

            if (queryParameters.isNotEmpty()) append("?")
            append(queryParameters.entries.joinToString("&") { "${it.key}=${it.value}" })
        }
}
