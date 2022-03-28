package dev.calebmiller.application

import dev.calebmiller.application.service.mapbox.MapboxService
import dev.calebmiller.application.service.mapbox.api.MapboxApiException
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
import kotlin.io.path.Path
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
class MapboxServiceTest: AutoCloseKoinTest() {

    @Test
    fun getForwardGeocoding_whenValidRequest_thenGeocodingDataReturned() = runTest {
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/mapboxApiResponses/forwardGeocodingResponse.json"
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
        val mapboxService = get<MapboxService>()

        val response = mapboxService.getForwardGeocoding("bay city", "mi")
        assertEquals("place.9715322999261580", response.features[0].id)
    }

    @Test
    fun getForwardGeocoding_whenInvalidRequest_thenExceptionThrown() = runTest {
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/mapboxApiResponses/invalidAccessTokenErrorResponse.json"
            )
        )
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val mapboxService = get<MapboxService>()

        val exception = assertFailsWith<MapboxApiException> {
            mapboxService.getForwardGeocoding("bay city", "mi")
        }
        assertEquals("Error fetching geocoding data from Mapbox API.", exception.message)
        assertEquals("Not Authorized - Invalid Token", exception.error.message)
    }

    @Test
    fun getCoordinates_whenValidRequest_thenCoordinatesReturned() = runTest {
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/mapboxApiResponses/forwardGeocodingResponse.json"
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
        val mapboxService = get<MapboxService>()

        val coordinates = mapboxService.getCoordinates("bay city", "mi")
        assertEquals(-83.8889, coordinates[0])
        assertEquals(43.5945, coordinates[1])
    }

    @Test
    fun getCoordinates_whenInvalidRequest_thenExceptionThrown() = runTest {
        val responseBody = Files.readAllBytes(
            Path(
                "src/test/resources/mapboxApiResponses/invalidAccessTokenErrorResponse.json"
            )
        )
        val mockEngine = MockEngine { request ->
            respond(
                content = ByteReadChannel(responseBody),
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        startKoin { modules(getAppTestModule(mockEngine)) }
        val mapboxService = get<MapboxService>()

        val exception = assertFailsWith<MapboxApiException> {
            mapboxService.getCoordinates("bay city", "mi")
        }
        assertEquals("Error fetching geocoding data from Mapbox API.", exception.message)
        assertEquals("Not Authorized - Invalid Token", exception.error.message)
    }
}