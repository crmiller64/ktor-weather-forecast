package dev.calebmiller.application.service.mapbox.api

import dev.calebmiller.application.service.weather.api.WeatherApiError
import kotlinx.serialization.Serializable

@Serializable
data class MapboxApiError(
    val message: String
)

class MapboxApiException(
    message: String,
    val error: MapboxApiError
) : Exception(message)