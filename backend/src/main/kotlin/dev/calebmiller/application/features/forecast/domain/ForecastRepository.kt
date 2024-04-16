package dev.calebmiller.application.features.forecast.domain

import dev.calebmiller.application.features.forecast.data.remote.MapboxService
import dev.calebmiller.application.features.forecast.data.remote.OpenWeatherService
import dev.calebmiller.application.features.forecast.domain.mapper.toDTO
import dev.calebmiller.application.features.forecast.domain.model.CurrentForecastDTO
import dev.calebmiller.application.features.forecast.domain.model.ForecastDTO

class ForecastRepository(private val mapboxService: MapboxService, private val openWeatherService: OpenWeatherService) {

    suspend fun getForecast(city: String, state: String): ForecastDTO {
        val forwardGeocode = mapboxService.getForwardGeocoding(city, state)
        val coordinates = forwardGeocode.features[0].geometry.coordinates
        val oneCall = openWeatherService.getWeatherForecast(coordinates[1], coordinates[0])

        val forecast = oneCall.toDTO()
        forecast.placeName = forwardGeocode.features[0].placeName

        return forecast
    }

    suspend fun getCurrentForecast(city: String, state: String): CurrentForecastDTO {
        val forwardGeocode = mapboxService.getForwardGeocoding(city, state)
        val coordinates = forwardGeocode.features[0].geometry.coordinates
        val current = openWeatherService.getCurrentWeatherForecast(coordinates[1], coordinates[0])

        val forecast = current.toDTO()
        forecast.placeName = forwardGeocode.features[0].placeName

        return forecast
    }
}