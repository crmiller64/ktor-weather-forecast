package dev.calebmiller.application.features.forecast.data.dto.mapbox

import kotlinx.serialization.Serializable

@Serializable
data class MapboxApiError(
    val message: String
)

class MapboxApiException(
    message: String,
    val error: MapboxApiError
) : Exception(message)