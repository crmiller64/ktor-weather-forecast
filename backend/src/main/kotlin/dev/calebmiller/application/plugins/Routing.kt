package dev.calebmiller.application.plugins

import dev.calebmiller.application.features.forecast.resource.forecastEndpoint
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import io.ktor.server.resources.*
import io.ktor.server.plugins.openapi.*

fun Application.configureRouting() {
    install(Resources)

    routing {
        // Serve the frontend app
        singlePageApplication {
            useResources = true
            filesPath = "public"
            defaultPage = "index.html"
        }
        openAPI(path = "doc", swaggerFile = "openapi/oas3.yaml")
        forecastEndpoint()
    }
}
