package dev.calebmiller.application

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import mu.KotlinLogging

class WeatherService {

    private val logger = KotlinLogging.logger {}

    private val weatherApiHost = "api.weather.gov"

    private val client = HttpClient(CIO) {
        install(Logging)
        install(JsonFeature) {
            serializer = KotlinxSerializer()
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
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): JsonObject {
        getWeatherForecastUrl(latitude, longitude)?.let { url ->
            return client.get(url)
        }
        return JsonObject(emptyMap())
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
                        try {
                            return Url(forecast.content)
                        } catch (e: URLParserException) {
                            logger.error(e) { "unable to parse forecast url" }
                        }
                    }
                }
            }
        }

        return null
    }
}