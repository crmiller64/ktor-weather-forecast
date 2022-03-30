package dev.calebmiller.application.features.forecast.domain.model

import dev.calebmiller.application.util.OffsetDateTimeSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class DailyForecastDTO(
    @Serializable(OffsetDateTimeSerializer::class)
    val generatedAt: OffsetDateTime,
    val periods: List<DailyForecastDTOPeriod> = emptyList()
)

@Serializable
data class DailyForecastDTOPeriod(
    val number: Int,
    val name: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val startTime: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val endTime: OffsetDateTime,
    val temperature: Int,
    val temperatureUnit: String,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String
)
