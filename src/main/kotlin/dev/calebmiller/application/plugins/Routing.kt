package dev.calebmiller.application.plugins

import dev.calebmiller.application.features.forecast.resource.forecastEndpoint
import io.ktor.application.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.configureRouting() {
    install(Locations)

    routing {
        forecastEndpoint()
        get("/") {
            call.respondText("This is a sample Ktor backend to get weather forecast data.")
        }
    }
}
