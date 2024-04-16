package dev.calebmiller.application.features.forecast.data.dto.openweather.current

import dev.calebmiller.application.util.UnixDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime

@Serializable
data class OpenWeatherCurrent(
    @SerialName("coord")
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    @SerialName("dt")
    @Serializable(UnixDateTimeSerializer::class)
    val date: OffsetDateTime,
    @SerialName("sys")
    val system: System
)

@Serializable
data class Coordinates(
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class Main(
    @SerialName("temp")
    val temperature: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val minimumTemperature: Double,
    @SerialName("temp_max")
    val maximumTemperature: Double,
    val pressure: Int,
    val humidity: Int
)

@Serializable
data class Wind(
    val speed: Double,
    @SerialName("deg")
    val direction: Double,
    val gust: Double? = null // Optional field
)

@Serializable
data class Clouds(
    // Cloudiness %
    @SerialName("all")
    val cloudinessPercent: Int
)

@Serializable
data class System(
    val country: String,
    @Serializable(UnixDateTimeSerializer::class)
    val sunrise: OffsetDateTime,
    @Serializable(UnixDateTimeSerializer::class)
    val sunset: OffsetDateTime
)