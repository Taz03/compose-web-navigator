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

NavHost(navController = navController) {
    route("/") {
        Text("Home")
    }
    route("/about") {
        Text("About")
    }
    route("/user/{id}") { route ->
        Text("User ID: ${route.pathParameters["id"]}")
        Text("Query: ${route.queryParameters["query"]}")
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

### Production Deployment

For production deployment, build the release binaries with:
```bash
gradle buildWebReleaseBinaries
```
And server with your preferred static file server. Configure the server to serve `index.html` for all routes to support client-side routing.
