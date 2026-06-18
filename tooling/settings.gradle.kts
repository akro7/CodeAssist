// AKRO tooling composite build — the Gradle plugin + tooling-API server that is injected into
// user projects so the IDE can query their Gradle build (symbol resolution, dependency graph, etc.).
// This is a standalone composite build; it does NOT share the IDE's libs catalog or modules.

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

rootProject.name = "akro-tooling"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        google()
        maven { url = uri("https://jitpack.io") }
    }
}

include(
    ":tooling-events",
    ":tooling-model",
    ":tooling-builder-model-impl",
    ":tooling-api",
    ":tooling-plugin-config",
    ":tooling-impl",
    ":tooling-plugin",
)

// Map sub-directory names → project paths
project(":tooling-events").projectDir              = file("events")
project(":tooling-model").projectDir               = file("model")
project(":tooling-builder-model-impl").projectDir  = file("builder-model-impl")
project(":tooling-api").projectDir                 = file("api")
project(":tooling-plugin-config").projectDir       = file("plugin-config")
project(":tooling-impl").projectDir                = file("impl")
project(":tooling-plugin").projectDir              = file("plugin")
