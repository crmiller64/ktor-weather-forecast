package dev.calebmiller.application.service.weather

import dev.calebmiller.application.service.weather.api.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import mu.KotlinLogging

class WeatherService(engine: HttpClientEngine) {

    private val logger = KotlinLogging.logger {}

    private val weatherApiHost = "api.weather.gov"

    private val client = HttpClient(engine) {
        install(Logging)
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
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

    /**
     * Get the weather forecast from the weather.gov API using the given coordinates.
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     */
    @Throws(WeatherApiException::class)
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): WeatherForecast {
        val weatherGrid = getWeatherGrid(latitude, longitude)

        val response: HttpResponse = client.get(weatherGrid.properties.forecast)

        if (response.status == HttpStatusCode.OK) {
                return response.receive()
        } else {
            // error received when fetching forecast data
            throw WeatherApiException("Error fetching weather forecast data.", response.receive())
        }
    }

    /**
     * Get the weather grid data from the weather.gov API using the given coordinates.
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     */
    @Throws(WeatherApiException::class)
    suspend fun getWeatherGrid(latitude: Double, longitude: Double): WeatherGrid {
        val response: HttpResponse = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = weatherApiHost
                path("points", "${latitude},${longitude}")
            }
        }

        if (response.status == HttpStatusCode.OK) {
            return response.receive()
        } else {
            throw WeatherApiException("Error fetching weather grid data.", response.receive())
        }
    }
}