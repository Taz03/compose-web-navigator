# Compose Web Navigator

Compose Web Navigator is a library that provides navigation capabilities for Compose Multiplatform Wasm applications. It allows developers to easily manage navigation between different screens and handle browser history.

### Installation
To use the Compose Web Navigator in your project, add the following to your `build.gradle.kts` file:

```kotlin
plugins {
    id("io.github.taz03.compose-web-navigator") version "<version>"
}

kotlin {
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets.commonMain.dependencies {
        implementation("io.github.taz03:compose-web-navigator:<version>")
    }
}
```

### Usage

```kotlin
val navController = rememberSaveable { NavController() }

NavHost(
    navController = navController,
    title = "My App"
) {
    route(
        path = "/",
        titleBuilder = { "Home | My App" }
    ) {
        Text("Home")
    }
    route(path = "/about") {
        Text("About")

        Button({
            Route(
                path = "/user/{id}",
                pathParameters = mapOf("id" to "john"),
                args = mapOf("status" to 123)
            ).let(navController::navigate)
        }) {
            Text("John")
        }
    }
    route(
        path = "/user/{id}",
        titleBuilder = { route ->
            "${route.pathParameters["id"]} - User | Demo"
        }
    ) { route ->
        Text("User ID: ${route.pathParameters["id"]}")
        Text("Query: ${route.queryParameters["query"]}")
        Text("Args: ${route.args}")
    }

    `404`(titleBuilder = { "404 | Demo" }) {
        Text("404 Not found")
    }
}
```

Supported path patterns:
- Static paths: `/home`, `/about`
- Path parameters: `/user/{id}`
- Wildcards: `/files/*path`

### Testing

To run the application in a development server, use:
```bash
gradle runWebServer
```
Don't use this command for production deployment, as it is intended for development purposes only.

### Production Deployment

For production deployment, build the release binaries with:
```bash
gradle wasmJsBrowserDistribution
```
This generates a distributable bundle at `./build/dist/wasmJs/productionExecutable`.

Serve the generated files with your preferred static file server.
Configure the server to serve `index.html` as fallback for all routes to support client-side routing.

Ktor example server:
```kotlin
// Replace with the absolute path to your distDir directory
val distDir = File("/path/to/your/distDir")

embeddedServer(CIO, port = 8080) {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            val file = File(distDir, "index.html")
            call.respondText(file.readText(), ContentType.Text.Html, status)
        }
    }

    routing {
        staticFiles("/", distDir) {
            default("index.html")
        }
    }
}
    .start(true)
    .stopSuspend()
```

Nginx example config:
```nginx
server {
    listen 8080;
    server_name localhost;

    # Replace with the absolute path to your distDir directory
    root /path/to/your/distDir;

    # Default file to serve if a directory is requested
    index index.html;

    location / {
        # Try to serve the requested URI directly as a file or directory.
        # If neither exists, fall back to index.html (SPA routing).
        try_files $uri $uri/ /index.html;
    }
}
```
