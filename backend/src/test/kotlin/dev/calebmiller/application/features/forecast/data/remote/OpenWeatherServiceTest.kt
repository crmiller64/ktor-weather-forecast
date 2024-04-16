package dev.calebmiller.application.features.forecast.data.remote

import dev.calebmiller.application.features.forecast.data.dto.openweather.OpenWeatherApiException
import dev.calebmiller.application.getAppTestModule
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.test.get
import org.koin.test.junit5.AutoCloseKoinTest
import java.nio.file.Files
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.io.path.Path
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class OpenWeatherServiceTest : AutoCloseKoinTest() {

    @Test
    fun getOneCallForecast_whenValidRequest_thenForecastDataReturned() = runTest {
        // Arrange
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/openWeatherApiResponses/oneCallResponse.json"
            )
        )
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val openWeatherService = get<OpenWeatherService>()

        // Act
        val response = openWeatherService.getWeatherForecast(90.0, 90.0)

        // Assert
        assertEquals(43.3, response.current.temperature)
        assertEquals(48, response.hourly.size)
        assertEquals(8, response.daily.size)
    }

    @Test
    fun getOneCallForecast_whenInvalidRequest_thenExceptionThrown() = runTest {
        // Arrange
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/openWeatherApiResponses/oneCallUnauthorizedResponse.json"
            )
        )
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val openWeatherService = get<OpenWeatherService>()

        // Act
        val exception = assertFailsWith<OpenWeatherApiException> {
            openWeatherService.getWeatherForecast(90.0, 90.0)
        }

        // Assert
        assertEquals("Error fetching weather forecast data from OpenWeather API.", exception.message)
        assertContains(exception.error.message, "Invalid API key.")
        assertEquals(401, exception.error.code)
    }

    @Test
    fun getCurrentWeatherForecast_whenValidRequest_thenForecastDataReturned() = runTest {
        // Arrange
        val responseBody = Files.readAllBytes(
                Path(
                        "src/test/resources/openWeatherApiResponses/currentWeatherResponse.json"
                )
        )
        val mockEngine = MockEngine { request ->
            respond(
                    content = ByteReadChannel(responseBody),
                    status = HttpStatusCode.OK,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val openWeatherService = get<OpenWeatherService>()

        // Act
        val response = openWeatherService.getCurrentWeatherForecast(90.0, 90.0)

        // Assert
        assertEquals("clear sky", response.weather[0].description)
        assertEquals(48.25, response.main.temperature)
        assertEquals(10.36, response.wind.speed)
        assertEquals(
            LocalDateTime.of(2024, 4, 16, 10, 50, 10),
            response.system.sunrise.toLocalDateTime())
        assertEquals(LocalDate.of(2024, 4, 16), response.date.toLocalDate())
    }

    @Test
    fun getCurrentForecast_whenInvalidRequest_thenExceptionThrown() = runTest {
        // Arrange
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/openWeatherApiResponses/oneCallUnauthorizedResponse.json"
            )
        )
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.InternalServerError,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val openWeatherService = get<OpenWeatherService>()

        // Act
        val exception = assertFailsWith<OpenWeatherApiException> {
            openWeatherService.getCurrentWeatherForecast(90.0, 90.0)
        }

        // Assert
        assertEquals("Error fetching weather forecast data from OpenWeather API.", exception.message)
        assertContains(exception.error.message, "Invalid API key.")
        assertEquals(401, exception.error.code)
    }
}