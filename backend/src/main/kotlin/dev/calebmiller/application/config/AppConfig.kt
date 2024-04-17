package dev.calebmiller.application.config

import io.ktor.server.application.*
import org.koin.ktor.ext.inject

const val MAPBOX_ACCESS_TOKEN_KEY = "ktor.mapbox.accessToken"
const val OPEN_WEATHER_ACCESS_TOKEN_KEY = "ktor.openWeather.accessToken"

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

/*
 * Read in environment variables and store them in an injectable AppConfig class, to be used by other
 * services.
 */
fun Application.setupConfig() {
    val appConfig: AppConfig by inject()

    val mapboxAccessToken =
        environment.config.propertyOrNull(MAPBOX_ACCESS_TOKEN_KEY)?.getString() ?: ""
    appConfig.mapboxConfig = MapboxConfig(mapboxAccessToken)

    val openWeatherAccessToken =
        environment.config.propertyOrNull(OPEN_WEATHER_ACCESS_TOKEN_KEY)?.getString() ?: ""
    appConfig.openWeatherConfig = OpenWeatherConfig(openWeatherAccessToken)
}