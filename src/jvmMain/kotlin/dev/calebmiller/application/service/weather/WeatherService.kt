package dev.calebmiller.application.service.weather

import Forecast
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
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
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): Forecast? {
        val weatherGrid = getWeatherGrid(latitude, longitude)
        val forecastUrl = parseUrl(weatherGrid.properties.forecast)

        return forecastUrl?.let { url ->
            val response = client.get<WeatherForecast>(url)
            return toForecast(
                weatherGrid.properties.relativeLocation.properties.city,
                weatherGrid.properties.relativeLocation.properties.state,
                response
            )
        }
    }

    /**
     * Get the forecast url for a given set of coordinates (latitude + longitude).
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     * @return url for the forecast at the given coordinates, else null if a problem occurred getting the url from
     * the weather.gov API
     */
    suspend fun getWeatherForecastUrl(latitude: Double, longitude: Double): Url? {
        val responseBody: JsonObject = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = weatherApiHost
                path("points", "${latitude},${longitude}")
            }
        }

        responseBody["properties"]?.let { properties ->
            if (properties is JsonObject) {
                properties["forecast"]?.let { forecast ->
                    if (forecast is JsonPrimitive && forecast.isString) {
                        return parseUrl(forecast.content)
                    }
                }
            }
        }

        return null
    }

    /**
     * Get the weather grid data from the weather.gov API using the given coordinates.
     *
     * @param latitude the latitude in decimal form
     * @param longitude the longitude in decimal form
     */
    suspend fun getWeatherGrid(latitude: Double, longitude: Double): WeatherGrid {
        return client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = weatherApiHost
                path("points", "${latitude},${longitude}")
            }
        }
    }

    private fun parseUrl(url: String): Url? {
        try {
            return Url(url)
        } catch (e: URLParserException) {
            logger.error(e) { "unable to parse url" }
        }
        return null
    }
}