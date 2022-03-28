package dev.calebmiller.application.config

import io.ktor.application.*
import org.koin.ktor.ext.inject

class AppConfig {
    lateinit var mapboxConfig: MapboxConfig
}

fun Application.setupConfig() {
    val appConfig: AppConfig by inject()

    val mapboxObject = environment.config.config("ktor.mapbox")
    val accessToken = mapboxObject.property("accessToken").getString()
    appConfig.mapboxConfig = MapboxConfig(accessToken)
}

data class MapboxConfig(
    val accessToken: String
)