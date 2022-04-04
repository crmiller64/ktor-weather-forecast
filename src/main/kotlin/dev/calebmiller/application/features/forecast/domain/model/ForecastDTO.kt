package dev.calebmiller.application.features.forecast.domain.model

import dev.calebmiller.application.util.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class ForecastDTO(
    val current: Current,
    val daily: List<Day>,
    val hourly: List<Hour>
)

@Serializable
data class Current(
    @Serializable(OffsetDateTimeSerializer::class)
    val date: OffsetDateTime,
    val temperature: Double,
    val temperatureUnit: String,
    val feelsLike: Double,
    val windSpeed: Double,
    val windDirection: String,
    val description: String
)

@Serializable
data class Day(
    @Serializable(OffsetDateTimeSerializer::class)
    val date: OffsetDateTime,
    val name: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val sunrise: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val sunset: OffsetDateTime,
    val highTemperature: Double,
    val lowTemperature: Double,
    val temperatureUnit: String,
    val windSpeed: Double,
    val windDirection: String,
    val description: String,
    val humidity: Int
)

@Serializable
data class Hour(
    @Serializable(OffsetDateTimeSerializer::class)
    val date: OffsetDateTime,
    val temperature: Double,
    val temperatureUnit: String,
    val feelsLike: Double,
    val windSpeed: Double,
    val windDirection: String,
    val description: String,
    val probabilityOfPrecipitation: Double
)