package dev.calebmiller.application

import dev.calebmiller.application.config.AppConfig
import dev.calebmiller.application.config.MapboxConfig
import dev.calebmiller.application.config.OpenWeatherConfig
import dev.calebmiller.application.features.forecast.data.remote.MapboxService
import dev.calebmiller.application.features.forecast.data.remote.OpenWeatherService
import io.ktor.client.engine.*
import org.koin.core.module.Module
import org.koin.dsl.module

fun getTestAppConfig(): AppConfig {

    return AppConfig().apply {
        mapboxConfig = MapboxConfig("test")
        openWeatherConfig = OpenWeatherConfig("test")
    }
}

fun getAppTestModule(engine: HttpClientEngine): Module {
    return module {
        single { getTestAppConfig() }
        single { MapboxService(engine, get()) }
        single { OpenWeatherService(engine, get(), get()) }
    }
}