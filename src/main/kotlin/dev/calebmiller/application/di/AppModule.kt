package dev.calebmiller.application.di

import dev.calebmiller.application.config.AppConfig
import dev.calebmiller.application.features.forecast.data.remote.MapboxService
import dev.calebmiller.application.features.forecast.data.remote.WeatherService
import dev.calebmiller.application.features.forecast.domain.ForecastRepository
import io.ktor.client.engine.cio.*
import org.koin.dsl.module

val appModule = module {
    single { CIO.create() }
    single { AppConfig() }
    single { MapboxService(get(), get()) }
    single { WeatherService(get(), get()) }
    single { ForecastRepository(get()) }
}