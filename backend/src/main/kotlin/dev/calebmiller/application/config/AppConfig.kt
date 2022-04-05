package dev.calebmiller.application.config

import io.ktor.application.*
import org.koin.ktor.ext.inject

class AppConfig {
    lateinit var mapboxConfig: MapboxConfig
    lateinit var openWeatherConfig: OpenWeatherConfig
}

fun Application.setupConfig() {
    val appConfig: AppConfig by inject()

    val mapboxObject = environment.config.config("ktor.mapbox")
    val mapboxAccessToken = mapboxObject.property("accessToken").getString()
    appConfig.mapboxConfig = MapboxConfig(mapboxAccessToken)

    val openWeatherObject = environment.config.config("ktor.openWeather")
    val openWeatherAccessToken = openWeatherObject.property("accessToken").getString()
    appConfig.openWeatherConfig = OpenWeatherConfig(openWeatherAccessToken)
}

data class MapboxConfig(
    val accessToken: String
)

data class OpenWeatherConfig(
    val accessToken: String
)