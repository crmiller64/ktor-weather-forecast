package dev.calebmiller.application.features.forecast.domain

import dev.calebmiller.application.features.forecast.data.dto.openweather.OpenWeatherApiException
import dev.calebmiller.application.features.forecast.data.remote.OpenWeatherService
import dev.calebmiller.application.features.forecast.domain.mapper.toDTO
import dev.calebmiller.application.features.forecast.domain.model.ForecastDTO

class ForecastRepository(private val openWeatherService: OpenWeatherService) {

    suspend fun getForecast(city: String, state: String): ForecastDTO {
        try {
            val forecast = openWeatherService.getWeatherForecast(city, state)
            return forecast.toDTO()
        } catch (e: OpenWeatherApiException) {
            throw Exception(e.message)
        }
    }
}