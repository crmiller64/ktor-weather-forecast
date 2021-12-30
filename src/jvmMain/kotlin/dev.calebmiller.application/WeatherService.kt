package dev.calebmiller

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class WeatherService {

    private val client = HttpClient(CIO) {
        install(Logging)
        expectSuccess = false
    }

    /**
     * Get the weather forecast from the weather.gov API using the given url.
     *
     * @param url the weather.gov url for the specific grid, for example:
     * https://api.weather.gov/gridpoints/DTX/32,89/forecast
     */
    suspend fun getWeatherForecast(url: String): HttpResponse {
        return client.get(url)
    }
}