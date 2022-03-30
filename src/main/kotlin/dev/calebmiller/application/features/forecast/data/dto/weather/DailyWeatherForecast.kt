package dev.calebmiller.application.features.forecast.data.dto.weather

import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

/* Representation of daily forecast data received from the weather.gov API. */

@Serializable
data class DailyWeatherForecast(
    val properties: DailyWeatherForecastProperties
)

@Serializable
data class DailyWeatherForecastProperties(
    @Serializable(OffsetDateTimeSerializer::class)
    val generatedAt: OffsetDateTime,
    val periods: List<DailyWeatherForecastPeriod>
)

@Serializable
data class DailyWeatherForecastPeriod(
    val number: Int,
    val name: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val startTime: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val endTime: OffsetDateTime,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val temperatureTrend: String? = null,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String
)
