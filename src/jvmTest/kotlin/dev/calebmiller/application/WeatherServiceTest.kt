package dev.calebmiller.application

import dev.calebmiller.application.service.weather.WeatherService
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherServiceTest {

    @Test
    fun getWeatherForecast_whenValidGrid_thenResponseSuccess() = runTest {
        val responseBody = Files.readAllBytes(Path("src/jvmTest/resources/forecastResponse.json"))

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val weatherService = WeatherService(mockEngine)
        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/32,89/forecast")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun getWeatherForecast_whenInvalidGrid_thenResponseServerError() = runTest {
        val responseBody = Files.readAllBytes(Path("src/jvmTest/resources/forecastErrorResponse.json"))

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/problem+json")
            )
        }

        val weatherService = WeatherService(mockEngine)
        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/0,0/forecast")
        assertEquals(HttpStatusCode.InternalServerError, response.status)
    }

    @Test
    fun getWeatherForecastUrl_whenValidCoordinates_thenNonNullUrl() = runTest {
        val responseBody = Files.readAllBytes(Path("src/jvmTest/resources/weatherGridResponse.json"))

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val weatherService = WeatherService(mockEngine)
        val response = weatherService.getWeatherForecastUrl(39.7456, -97.0892)
        assertNotNull(response)
    }

    @Test
    fun getWeatherForecast_whenValidCoordinates_thenNonNullResponse() = runTest {
        val weatherGridResponseBody = Files.readAllBytes(Path("src/jvmTest/resources/weatherGridResponse.json"))
        val forecastResponseBody = Files.readAllBytes(Path("src/jvmTest/resources/forecastResponse.json"))

        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath.startsWith("/points")) {
                respond(
                    content = ByteReadChannel(weatherGridResponseBody),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else if (request.url.encodedPath.endsWith("/forecast")) {
                respond(
                    content = ByteReadChannel(forecastResponseBody),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                error("Unhandled ${request.url.encodedPath}")
            }

        }

        val weatherService = WeatherService(mockEngine)
        val response = weatherService.getWeatherForecast(39.7456, -97.0892)
        assertNotNull(response)
    }

    @Test
    fun getWeatherGrid_whenValidCoordinates_thenNonNullResponse() = runTest {
        val responseBody = Files.readAllBytes(Path("src/jvmTest/resources/weatherGridResponse.json"))

        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val weatherService = WeatherService(mockEngine)
        val response = weatherService.getWeatherGrid(39.7456, -97.0892)
        assertNotNull(response)
    }
}