package io.github.taz03.compose.web.navigator

data class Route(
    val path: String,
    val pathParameters: Map<String, String> = emptyMap(),
    val queryParameters: Map<String, String> = emptyMap()
) {
    val url get() = buildString {
        var compiledPath = path
        pathParameters.forEach {
           compiledPath = compiledPath.replace("{${it.key}}".toRegex(RegexOption.IGNORE_CASE), it.value)
        }
        append(compiledPath)

        if (queryParameters.isNotEmpty()) {
            append("?")
            append(queryParameters.entries.joinToString("&") { "${it.key}=${it.value}" })
        }
    }
}
