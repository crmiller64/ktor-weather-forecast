package dev.calebmiller.application.features.forecast.resource

import dev.calebmiller.application.features.forecast.domain.ForecastRepository
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

/* ROUTE / ENDPOINT DEFINITIONS */
@Resource("/forecast")
class ForecastEndpoint {

    @Resource("")
    class Forecast(val parent: ForecastEndpoint = ForecastEndpoint(), val city: String, val state: String)

    @Resource("/current")
    class CurrentForecast(val parent: ForecastEndpoint = ForecastEndpoint(), val city: String, val state: String)
}

// ROUTE / ENDPOINT HANDLERS
fun Route.forecastEndpoint() {

    val forecastRepository: ForecastRepository by inject()

    get<ForecastEndpoint.Forecast> { request ->
        try {
            call.respond(forecastRepository.getForecast(request.city, request.state))
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                "Error retrieving weather data. Please check server logs for details."
            )
        }
    }

    get<ForecastEndpoint.CurrentForecast> { request ->
        try {
            call.respond(forecastRepository.getCurrentForecast(request.city, request.state))
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                "Error retrieving weather data. Please check server logs for details."
            )
        }
    }
}