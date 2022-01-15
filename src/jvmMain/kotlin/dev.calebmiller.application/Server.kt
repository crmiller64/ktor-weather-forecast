package dev.calebmiller.application

import dev.calebmiller.application.service.weather.WeatherService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.respondHtml
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*

val weatherService = WeatherService()

fun HTML.index() {
    head {
        title("Ktor Weather Forecast")
        // bootstrap css
        link(rel = LinkRel.stylesheet) {
            href = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            integrity = "sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
            attributes["crossorigin"] = "anonymous"
        }
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/async-weather-forecast.js") {}
        // bootstrap js
        script {
            src = "https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity = "sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            attributes["crossorigin"] = "anonymous"
        }
    }
}

fun main() {
    embeddedServer(Netty, port = 9090) {
        install(ContentNegotiation) {
            json()
        }
        install(CORS) {
            method(HttpMethod.Get)
            method(HttpMethod.Post)
            method(HttpMethod.Delete)
            anyHost()
        }
        install(Compression) {
            gzip()
        }

        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            static("/static") {
                resources()
            }
            route("/weather") {
                get("/forecast") {
                    val latitude = call.request.queryParameters["latitude"]?.toDoubleOrNull()
                    val longitude = call.request.queryParameters["longitude"]?.toDoubleOrNull()
                    if (latitude != null && longitude != null) {
                        weatherService.getWeatherForecast(latitude, longitude)?.let {
                            call.respond(it)
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "the supplied coordinates are not valid")
                    }
                }
            }
        }
    }.start(wait = true)
}