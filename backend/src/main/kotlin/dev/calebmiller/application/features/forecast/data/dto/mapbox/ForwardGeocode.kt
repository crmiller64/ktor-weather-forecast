package dev.calebmiller.application.features.forecast.data.dto.mapbox

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

/* Representation of forward geocoding data received from the Mapbox API. */

@Serializable
data class ForwardGeocode(
    val type: String,
    val query: List<String>,
    val features: List<Feature>,
    val attribution: String
)

@Serializable
data class Feature(
    val id: String,
    @SerialName("place_name") val placeName: String,
    val geometry: Geometry,
)

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<Double>
)