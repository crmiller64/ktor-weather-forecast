package dev.calebmiller.application.features.forecast.data.dto.openweather.onecall

import dev.calebmiller.application.util.UnixDateTimeSerializer
import kotlinx.datetime.TimeZone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class OpenWeatherOneCall(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double,
    val timezone: TimeZone,
    // shift in seconds from UTC
    @SerialName("timezone_offset")
    val timezoneOffset: Int,
    val current: Current,
    val hourly: List<Hour>,
    val daily: List<Day>
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Current(
    @SerialName("dt")
    @Serializable(UnixDateTimeSerializer::class)
    val date: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val sunrise: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val sunset: OffsetDateTime,
    @SerialName("temp")
    val temperature: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("uvi")
    val uvIndex: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    // wind direction in degrees
    @SerialName("wind_deg")
    val windDirection: Double,
    val weather: List<Weather>
)

@Serializable
data class Hour(
    @SerialName("dt")
    @Serializable(UnixDateTimeSerializer::class)
    val date: OffsetDateTime,
    @SerialName("temp")
    val temperature: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("uvi")
    val uvIndex: Double,
    val clouds: Int,
    val visibility: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    // wind direction in degrees
    @SerialName("wind_deg")
    val windDirection: Double,
    @SerialName("wind_gust")
    val windGust: Double,
    val weather: List<Weather>,
    @SerialName("pop")
    val probabilityOfPrecipitation: Double
)

@Serializable
data class DayTemperature(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    @SerialName("eve")
    val evening: Double,
    @SerialName("morn")
    val morning: Double
)

@Serializable
data class DayFeelsLike(
    val day: Double,
    val night: Double,
    @SerialName("eve")
    val evening: Double,
    @SerialName("morn")
    val morning: Double
)

@Serializable
data class Day(
    @SerialName("dt")
    @Serializable(UnixDateTimeSerializer::class)
    val date: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val sunrise: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val sunset: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val moonrise: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val moonset: OffsetDateTime,
    @SerialName("moon_phase")
    val moonPhase: Double,
    @SerialName("temp")
    val temperature: DayTemperature,
    @SerialName("feels_like")
    val feelsLike: DayFeelsLike,
    val pressure: Int,
    val humidity: Int,
    @SerialName("dew_point")
    val dewPoint: Double,
    @SerialName("uvi")
    val uvIndex: Double,
    val clouds: Int,
    @SerialName("wind_speed")
    val windSpeed: Double,
    // wind direction in degrees
    @SerialName("wind_deg")
    val windDirection: Double,
    @SerialName("wind_gust")
    val windGust: Double,
    val weather: List<Weather>,
    @SerialName("pop")
    val probabilityOfPrecipitation: Double
)