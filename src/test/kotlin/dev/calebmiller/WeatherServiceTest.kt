package dev.calebmiller

import io.ktor.http.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.*

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
}