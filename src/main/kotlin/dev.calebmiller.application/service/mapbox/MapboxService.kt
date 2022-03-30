package dev.calebmiller.application.service.mapbox

import dev.calebmiller.application.config.AppConfig
import dev.calebmiller.application.service.mapbox.api.ForwardGeocode
import dev.calebmiller.application.service.mapbox.api.MapboxApiError
import dev.calebmiller.application.service.mapbox.api.MapboxApiException
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

class MapboxService(engine: HttpClientEngine, appConfig: AppConfig) {

    private val logger = KotlinLogging.logger {}

    private val mapboxApiHost = "api.mapbox.com"
    private val mapboxAccessToken = appConfig.mapboxConfig.accessToken

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
     * Get the geographic coordinates from the Mapbox API of the given city and state.
     *
     * @param city the city name
     * @param state the state name
     * @return the geographic coordinates for the given city and state, where the first element in the array is the
     * longitude and the second element is the latitude
     */
    @Throws(MapboxApiException::class)
    suspend fun getCoordinates(city: String, state: String): Array<Double> {
        val forwardGeocode = getForwardGeocoding(city, state)
        return forwardGeocode.features[0].geometry.coordinates
    }

    /**
     * Get the geocoding data from the Mapbox API using the given city and state.
     *
     * @param city the city name
     * @param state the state name
     */
    @Throws(MapboxApiException::class)
    suspend fun getForwardGeocoding(city: String, state: String): ForwardGeocode {
        val response: HttpResponse = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = mapboxApiHost
                path("geocoding", "v5", "mapbox.places", "${city} ${state}", ".json")
                parameter("limit", 1)
                parameter("access_token", mapboxAccessToken)
            }
        }

        if (response.status == HttpStatusCode.OK) {
            return response.receive()
        } else {
            val error: MapboxApiError = response.receive()
            logger.error{ "Error retrieving geocoding data: \n$error" }
            throw MapboxApiException("Error fetching geocoding data from Mapbox API.", error)
        }
    }
}