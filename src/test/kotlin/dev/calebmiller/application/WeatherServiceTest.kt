package dev.calebmiller.application

import dev.calebmiller.application.service.weather.WeatherService
import dev.calebmiller.application.service.weather.api.WeatherApiException
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.koin.test.get
import org.koin.core.context.startKoin
import org.koin.test.junit5.AutoCloseKoinTest
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherServiceTest: AutoCloseKoinTest() {

    @Test
    fun getWeatherForecast_whenValidGrid_thenResponseSuccess() = runTest {
        val responseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/forecastResponse.json"
        ))
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/32,89/forecast")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun getWeatherForecast_whenInvalidGrid_thenResponseServerError() = runTest {
        val responseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/forecastErrorResponse.json"
        ))
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/problem+json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val response = weatherService.getWeatherForecast("https://api.weather.gov/gridpoints/DTX/0,0/forecast")
        assertEquals(HttpStatusCode.InternalServerError, response.status)
    }

    @Test
    fun getWeatherForecast_whenValidCityState_thenNonNullResponse() = runTest {
        val forwardGeocodingResponseBody = Files.readAllBytes(Path(
            "src/test/resources/mapboxApiResponses/forwardGeocodingResponse.json"
        ))
        val weatherGridResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/weatherGridResponse.json"
        ))
        val forecastResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/forecastResponse.json"
        ))
        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath.startsWith("/geocoding")) {
                respond(
                    content = ByteReadChannel(forwardGeocodingResponseBody),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else if (request.url.encodedPath.startsWith("/points")) {
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

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val forecast = weatherService.getWeatherForecast("bay city", "mi")
        assertEquals(14, forecast.properties.periods.size)
    }

    @Test
    fun getWeatherForecast_whenValidCoordinates_thenNonNullResponse() = runTest {
        val weatherGridResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/weatherGridResponse.json"
        ))
        val forecastResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/forecastResponse.json"
        ))
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

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val forecast = weatherService.getWeatherForecast(39.7456, -97.0892)
        assertEquals(14, forecast.properties.periods.size)
    }

    @Test
    fun getWeatherForecast_whenWeatherApiServerErrorResponse_thenExceptionThrown() = runTest {
        val weatherGridResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/weatherGridResponse.json"
        ))
        val forecastResponseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/forecastErrorResponse.json"
        ))
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
                    status = HttpStatusCode.InternalServerError,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                error("Unhandled ${request.url.encodedPath}")
            }

        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val exception = assertFailsWith<WeatherApiException> {
            weatherService.getWeatherForecast(90.0, -180.0)
        }
        assertEquals("Error fetching weather forecast data from weather API.", exception.message)
        assertEquals("An unexpected problem has occurred.", exception.error.detail)
    }

    @Test
    fun getWeatherGrid_whenValidCoordinates_thenNonNullResponse() = runTest {
        val responseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/weatherGridResponse.json"
        ))
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val weatherGrid = weatherService.getWeatherGrid(39.7456, -97.0892)
        assertEquals(
            "https://api.weather.gov/gridpoints/TOP/31,80/forecast",
            weatherGrid.properties.forecast
        )
    }

    @Test
    fun getWeatherGrid_whenInvalidCoordinates_thenExceptionThrown() = runTest {
        val responseBody = Files.readAllBytes(Path(
            "src/test/resources/weatherGovApiResponses/weatherGridNotFoundResponse.json"
        ))
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val weatherService = get<WeatherService>()

        val exception = assertFailsWith<WeatherApiException> {
            weatherService.getWeatherGrid(90.0, -180.0)
        }
        assertEquals("Error fetching weather grid data from weather API.", exception.message)
        assertEquals("Unable to provide data for requested point 90,-180", exception.error.detail)
    }
}