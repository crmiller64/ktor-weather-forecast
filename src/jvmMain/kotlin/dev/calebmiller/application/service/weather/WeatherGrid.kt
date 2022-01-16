package dev.calebmiller.application.service.weather

import kotlinx.serialization.Serializable

@Serializable
data class WeatherGrid(
    val properties: WeatherGridProperties
)

@Serializable
data class WeatherGridProperties(
    val forecastOffice: String,
    val gridId: String,
    val gridX: Int,
    val gridY: Int,
    val forecast: String,
    val forecastHourly: String,
    val forecastGridData: String,
    val observationStations: String,
    val forecastZone: String,
    val county: String,
    val fireWeatherZone: String,
    val timeZone: String,
    val radarStation: String,
    val relativeLocation: WeatherGridRelativeLocation
)

@Serializable
data class WeatherGridRelativeLocation(
    val properties: WeatherGridRelativeLocationProperties
)

@Serializable
data class WeatherGridRelativeLocationProperties(
    val city: String,
    val state: String
)