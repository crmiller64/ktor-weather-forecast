package dev.calebmiller.application.features.forecast.domain

import dev.calebmiller.application.features.forecast.domain.model.DailyForecastDTO
import dev.calebmiller.application.features.forecast.data.dto.weather.WeatherApiException
import dev.calebmiller.application.features.forecast.data.remote.WeatherService
import dev.calebmiller.application.features.forecast.domain.mapper.toDTO
import dev.calebmiller.application.features.forecast.domain.model.HourlyForecastDTO

class ForecastRepository(private val weatherService: WeatherService) {

    suspend fun getDailyForecast(city: String, state: String): DailyForecastDTO {
        try {
            val dailyWeatherForecast = weatherService.getDailyWeatherForecast(city, state)
            return dailyWeatherForecast.toDTO()
        } catch (e: WeatherApiException) {
            throw Exception(e.message)
        }
    }

    suspend fun getHourlyForecast(city: String, state: String): HourlyForecastDTO {
        try {
            val hourlyWeatherForecast = weatherService.getHourlyWeatherForecast(city, state)
            return hourlyWeatherForecast.toDTO()
        } catch (e: WeatherApiException) {
            throw Exception(e.message)
        }
    }
}