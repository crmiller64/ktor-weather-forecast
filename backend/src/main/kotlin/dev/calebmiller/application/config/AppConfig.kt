package dev.calebmiller.application.config

import io.ktor.application.*
import org.koin.ktor.ext.inject

const val MAPBOX_CONFIG_KEY = "ktor.mapbox"
const val OPEN_WEATHER_CONFIG_KEY = "ktor.openWeather"

class AppConfig {
    lateinit var mapboxConfig: MapboxConfig
    lateinit var openWeatherConfig: OpenWeatherConfig
}

data class MapboxConfig(
    val accessToken: String
)

data class OpenWeatherConfig(
    val accessToken: String
)

fun Application.setupConfig() {
    val appConfig: AppConfig by inject()

    val mapboxObject = environment.config.config(MAPBOX_CONFIG_KEY)
    val mapboxAccessToken = mapboxObject.property("accessToken").getString()
    appConfig.mapboxConfig = MapboxConfig(mapboxAccessToken)

    val openWeatherObject = environment.config.config(OPEN_WEATHER_CONFIG_KEY)
    val openWeatherAccessToken = openWeatherObject.property("accessToken").getString()
    appConfig.openWeatherConfig = OpenWeatherConfig(openWeatherAccessToken)
}