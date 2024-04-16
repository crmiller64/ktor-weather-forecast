package dev.calebmiller.application.features.forecast.domain.model

import dev.calebmiller.application.util.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class CurrentForecastDTO (
    var placeName: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val date: OffsetDateTime,
    val temperature: Double,
    val temperatureUnit: String,
    val feelsLike: Double,
    val windSpeed: Double,
    val windDirection: String,
    val windUnit: String,
    val description: String
)