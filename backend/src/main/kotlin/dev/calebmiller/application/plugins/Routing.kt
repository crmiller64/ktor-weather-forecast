package dev.calebmiller.application.plugins

import dev.calebmiller.application.features.forecast.resource.forecastEndpoint
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.routing.*
import java.io.File

fun Application.configureRouting() {
    install(Locations)

    routing {
        static("/static") {
            staticBasePackage = "public/static"
            static("/js") {
                resources("js")
            }
            static("/css") {
                resources("css")
            }
        }
        static("/") {
            staticBasePackage = "public"
            resources(".")
            defaultResource("index.html")
        }
        forecastEndpoint()
    }
}
