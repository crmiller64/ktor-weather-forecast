package dev.calebmiller.application.di

import dev.calebmiller.application.config.AppConfig
import dev.calebmiller.application.service.mapbox.MapboxService
import dev.calebmiller.application.service.weather.WeatherService
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val appModule = module {
    single { CIO.create() }
    single { AppConfig() }
    single { MapboxService(get(), get()) }
    single { WeatherService(get(), get()) }
}