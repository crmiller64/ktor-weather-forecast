package dev.calebmiller.application.features.forecast.resource

import dev.calebmiller.application.features.forecast.domain.ForecastRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

@OptIn(KtorExperimentalLocationsAPI::class)
@Location("/forecast")
class ForecastEndpoint {

    @Location("")
    data class Forecast(val parent: ForecastEndpoint, val city: String, val state: String)
}

@OptIn(KtorExperimentalLocationsAPI::class)
fun Route.forecastEndpoint() {

    val forecastRepository: ForecastRepository by inject()

    get<ForecastEndpoint.Forecast> {
        try {
            call.respond(forecastRepository.getForecast(it.city, it.state))
        } catch (e: Exception) {
            call.respond(
                HttpStatusCode.InternalServerError,
                "Error retrieving weather data. Please check server logs for details."
            )
        }
    }
}