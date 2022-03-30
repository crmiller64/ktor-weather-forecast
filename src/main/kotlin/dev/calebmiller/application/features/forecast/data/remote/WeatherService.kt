package dev.calebmiller.application.features.forecast.data.remote

import dev.calebmiller.application.features.forecast.data.dto.weather.*
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

class WeatherService(engine: HttpClientEngine, private val mapboxService: MapboxService) {

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
    suspend fun getDailyWeatherForecast(url: String): HttpResponse {
        return client.get(url)
    }

    /**
     * Get the weather forecast from the weather.gov API using the given city and state.
     *
     * @param city the city name
     * @param state the state name
     */
    @Throws(WeatherApiException::class)
    suspend fun getDailyWeatherForecast(city: String, state: String): DailyWeatherForecast {
        val coordinates = mapboxService.getCoordinates(city, state)
        return getDailyWeatherForecast(coordinates[1], coordinates[0])
    }

    /**
     * Get the weather forecast from the weather.gov API using the given coordinates.
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     */
    @Throws(WeatherApiException::class)
    suspend fun getDailyWeatherForecast(latitude: Double, longitude: Double): DailyWeatherForecast {
        val weatherGrid = getWeatherGrid(latitude, longitude)

        val response: HttpResponse = client.get(weatherGrid.properties.forecast)

        if (response.status == HttpStatusCode.OK) {
            return response.receive()
        } else {
            // error received when fetching forecast data
            val error: WeatherApiError = response.receive()
            logger.error{ "Error retrieving daily weather forecast data: \n$error" }
            throw WeatherApiException("Error fetching daily weather forecast data from weather API.", error)
        }
    }

    /**
     * Get the hourly weather forecast from the weather.gov API using the given city and state.
     *
     * @param city the city name
     * @param state the state name
     */
    @Throws(WeatherApiException::class)
    suspend fun getHourlyWeatherForecast(city: String, state: String): HourlyWeatherForecast {
        val coordinates = mapboxService.getCoordinates(city, state)
        return getHourlyWeatherForecast(coordinates[1], coordinates[0])
    }

    /**
     * Get the hourly weather forecast from the weather.gov API using the given coordinates.
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     */
    @Throws(WeatherApiException::class)
    suspend fun getHourlyWeatherForecast(latitude: Double, longitude: Double): HourlyWeatherForecast {
        val weatherGrid = getWeatherGrid(latitude, longitude)

        val response: HttpResponse = client.get(weatherGrid.properties.forecastHourly)

        if (response.status == HttpStatusCode.OK) {
            return response.receive()
        } else {
            // error received when fetching forecast data
            val error: WeatherApiError = response.receive()
            logger.error{ "Error retrieving hourly weather forecast data: \n$error" }
            throw WeatherApiException("Error fetching hourly weather forecast data from weather API.", error)
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
            val error: WeatherApiError = response.receive()
            logger.error{ "Error retrieving weather grid data: \n$error" }
            throw WeatherApiException("Error fetching weather grid data from weather API.", error)
        }
    }
}