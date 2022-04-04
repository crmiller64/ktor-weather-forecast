package dev.calebmiller.application.features.forecast.domain.mapper

import dev.calebmiller.application.features.forecast.data.dto.openweather.OpenWeatherOneCall
import dev.calebmiller.application.features.forecast.domain.model.*
import kotlin.math.roundToInt

fun OpenWeatherOneCall.toDTO(): ForecastDTO {
    val current = Current(
        this.current.date,
        this.current.temperature,
        "F",
        this.current.feelsLike,
        this.current.windSpeed,
        toDirection(this.current.windDirection),
        this.current.weather[0].description.replaceFirstChar { it.uppercaseChar() }
    )

    val days = this.daily.map { d ->
        Day(
            d.date,
            d.date.dayOfWeek.name,
            d.sunrise,
            d.sunset,
            d.temperature.max,
            d.temperature.min,
            "F",
            d.windSpeed,
            toDirection(d.windDirection),
            d.weather[0].description.replaceFirstChar { it.uppercaseChar() },
            d.humidity
        )
    }

    val hours = this.hourly.map { h ->
        Hour(
            h.date,
            h.temperature,
            "F",
            h.feelsLike,
            h.windSpeed,
            toDirection(h.windDirection),
            h.weather[0].description.replaceFirstChar { it.uppercaseChar() },
            h.probabilityOfPrecipitation
        )
    }

    return ForecastDTO(current, days, hours)
}

// Convert a wind direction in degrees to a string. Taken from: https://stackoverflow.com/a/61077325
private fun toDirection(windDirection: Double): String {
    val directions = arrayOf(
        "N",
        "NE",
        "E",
        "SE",
        "S",
        "SW",
        "W",
        "NW"
    )
    // split into the 8 directions
    val degrees = windDirection * 8 / 360
    // round to the nearest integer
    var direction = degrees.roundToInt()
    // ensure it's within 0-7
    direction = (direction + 8) % 8
    return directions[direction]
}