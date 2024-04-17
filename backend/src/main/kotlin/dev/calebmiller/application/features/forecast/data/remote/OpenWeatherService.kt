package dev.calebmiller.application.features.forecast.data.remote

import dev.calebmiller.application.config.AppConfig
import dev.calebmiller.application.features.forecast.data.dto.openweather.OpenWeatherApiError
import dev.calebmiller.application.features.forecast.data.dto.openweather.OpenWeatherApiException
import dev.calebmiller.application.features.forecast.data.dto.openweather.current.OpenWeatherCurrent
import dev.calebmiller.application.features.forecast.data.dto.openweather.onecall.OpenWeatherOneCall
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.http.*
import mu.KotlinLogging

class OpenWeatherService(engine: HttpClientEngine, appConfig: AppConfig) {

    private val logger = KotlinLogging.logger {}

    private val openWeatherApiHost = "api.openweathermap.org"
    private val openWeatherAccessToken = appConfig.openWeatherConfig.accessToken

    private val client = HttpClient(engine) {
        install(Logging)
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
        expectSuccess = false
    }

    @Throws(OpenWeatherApiException::class)
    suspend fun getWeatherForecast(latitude: Double, longitude: Double): OpenWeatherOneCall {
        val response: HttpResponse = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = openWeatherApiHost
                path("data", "2.5", "onecall")
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("exclude", "minutely,alerts")
                parameter("units", "imperial")
                parameter("appid", openWeatherAccessToken)
            }
        }

        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            // error received when fetching forecast data
            val error: OpenWeatherApiError = response.body()
            logger.error { "Error retrieving weather forecast data: \n$error" }
            throw OpenWeatherApiException("Error fetching weather forecast data from OpenWeather API.", error)
        }
    }

    @Throws(OpenWeatherApiException::class)
    suspend fun getCurrentWeatherForecast(latitude: Double, longitude: Double): OpenWeatherCurrent {
        val response: HttpResponse = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = openWeatherApiHost
                path("data", "2.5", "weather")
                parameter("lat", latitude)
                parameter("lon", longitude)
                parameter("units", "imperial")
                parameter("appid", openWeatherAccessToken)
            }
        }

        if (response.status == HttpStatusCode.OK) {
            return response.body()
        } else {
            // error received when fetching forecast data
            val error: OpenWeatherApiError = response.body()
            logger.error { "Error retrieving weather forecast data: \n$error" }
            throw OpenWeatherApiException("Error fetching weather forecast data from OpenWeather API.", error)
        }
    }
}