package dev.calebmiller.application.plugins

import dev.calebmiller.application.service.weather.WeatherService
import dev.calebmiller.application.service.weather.api.WeatherApiException
import dev.calebmiller.application.service.weather.api.toForecast
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {

    val weatherService: WeatherService by inject<WeatherService>()

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        route("/weather") {
            get("/forecast") {
                // TODO move to controller class?
                val latitude = call.request.queryParameters["latitude"]?.toDoubleOrNull()
                val longitude = call.request.queryParameters["longitude"]?.toDoubleOrNull()
                if (latitude != null && longitude != null) {
                    try {
                        call.respond(toForecast( weatherService.getWeatherForecast(latitude, longitude)))
                    } catch (e: WeatherApiException) {
                        call.respond(
                            HttpStatusCode.InternalServerError,
                            "Error retrieving weather data. Please check server logs for details"
                        )
                    }
                } else {
                    call.respond(HttpStatusCode.BadRequest, "The supplied coordinates are not valid.")
                }
            }
        }
    }
}
