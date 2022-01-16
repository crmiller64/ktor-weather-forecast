package dev.calebmiller.application

import dev.calebmiller.application.service.weather.WeatherService
import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherServiceTest {

    @Test
    fun getWeatherForecast_whenValidGrid_thenResponseSuccess() = runTest {
        val weatherService = WeatherService()
        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/32,89/forecast")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun getWeatherForecast_whenInvalidGrid_thenResponseServerError() = runTest {
        val weatherService = WeatherService()
        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/0,0/forecast")
        assertEquals(HttpStatusCode.InternalServerError, response.status)
    }

    @Test
    fun getWeatherForecastUrl_whenValidCoordinates_thenNonNullUrl() = runTest {
        val weatherService = WeatherService()
        val response = weatherService.getWeatherForecastUrl(39.7456, -97.0892)
        assertNotNull(response)
    }

    @Test
    fun getWeatherForecast_whenValidCoordinates_thenNonNullResponse() = runTest {
        val weatherService = WeatherService()
        val response = weatherService.getWeatherForecast(39.7456, -97.0892)
        assertNotNull(response)
    }

    @Test
    fun getWeatherGrid_whenValidCoordinates_thenNonNullResponse() = runTest {
        val weatherService = WeatherService()
        val response = weatherService.getWeatherGrid(39.7456, -97.0892)
        assertNotNull(response)
    }
}