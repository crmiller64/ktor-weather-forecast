package dev.calebmiller.application.service.weather.api

import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiError(
    val correlationId: String,
    val title: String,
    val type: String,
    val status: Int,
    val detail: String,
    val instance: String
)

class WeatherApiException(
    message: String,
    val error: WeatherApiError
) : Exception(message)