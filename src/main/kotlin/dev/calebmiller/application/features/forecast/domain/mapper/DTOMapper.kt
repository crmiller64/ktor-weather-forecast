package dev.calebmiller.application.features.forecast.domain.mapper

import dev.calebmiller.application.features.forecast.domain.model.DailyForecastDTO
import dev.calebmiller.application.features.forecast.domain.model.DailyForecastDTOPeriod
import dev.calebmiller.application.features.forecast.data.dto.weather.DailyWeatherForecast
import dev.calebmiller.application.features.forecast.data.dto.weather.HourlyWeatherForecast
import dev.calebmiller.application.features.forecast.domain.model.HourlyForecastDTO
import dev.calebmiller.application.features.forecast.domain.model.HourlyForecastDTOPeriod

fun DailyWeatherForecast.toDTO(): DailyForecastDTO {
    val periods = this.properties.periods.map { w ->
        DailyForecastDTOPeriod(
            w.number,
            w.name,
            w.startTime,
            w.endTime,
            w.temperature,
            w.temperatureUnit,
            w.windSpeed,
            w.windDirection,
            w.shortForecast,
            w.detailedForecast
        )
    }
    return DailyForecastDTO(this.properties.generatedAt, periods)
}

fun HourlyWeatherForecast.toDTO(): HourlyForecastDTO {
    val periods = this.properties.periods.map { w ->
        HourlyForecastDTOPeriod(
            w.number,
            w.startTime,
            w.endTime,
            w.temperature,
            w.temperatureUnit,
            w.windSpeed,
            w.windDirection,
            w.shortForecast
        )
    }
    return HourlyForecastDTO(this.properties.generatedAt, periods)
}