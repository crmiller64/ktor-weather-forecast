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
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/async-weather-forecast.js") {}
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
                        val forecast = weatherService.getWeatherForecast(latitude, longitude)
                        // TODO return weather forecast object
                        call.respond(HttpStatusCode.OK, "this is a valid request")
                    } else {
                        call.respond(HttpStatusCode.BadRequest, "the supplied coordinates are not valid")
                    }
                }
            }
        }
    }.start(wait = true)
}