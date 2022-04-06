package dev.calebmiller.application.plugins

import dev.calebmiller.application.features.forecast.resource.forecastEndpoint
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.locations.*
import io.ktor.routing.*

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
            // swagger assets
            resources("doc")
            // frontend assets
            resources("public")
            defaultResource("public/index.html")
        }
        static("/doc") {
            defaultResource("doc/index.html")
        }
        forecastEndpoint()
    }
}
