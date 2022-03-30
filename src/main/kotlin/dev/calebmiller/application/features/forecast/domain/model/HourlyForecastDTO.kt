package dev.calebmiller.application.features.forecast.domain.model

import dev.calebmiller.application.util.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class HourlyForecastDTO(
    @Serializable(OffsetDateTimeSerializer::class)
    val generatedAt: OffsetDateTime,
    val periods: List<HourlyForecastDTOPeriod> = emptyList()
)

@Serializable
data class HourlyForecastDTOPeriod(
    val number: Int,
    @Serializable(OffsetDateTimeSerializer::class)
    val startTime: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val endTime: OffsetDateTime,
    val temperature: Int,
    val temperatureUnit: String,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String
)
