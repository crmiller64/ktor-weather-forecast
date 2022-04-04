package dev.calebmiller.application.features.forecast.data.dto.openweather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherApiError(
    @SerialName("cod") val code: Int,
    val message: String
)

class OpenWeatherApiException(
    message: String,
    val error: OpenWeatherApiError
) : Exception(message)