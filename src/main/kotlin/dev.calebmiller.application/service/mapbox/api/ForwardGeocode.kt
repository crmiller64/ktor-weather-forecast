package dev.calebmiller.application.service.mapbox.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* Representation of forward geocoding data received from the Mapbox API. */

@Serializable
data class ForwardGeocode (
    val type: String,
    val query: Array<String>,
    val features: Array<ForwardGeocodeFeature>,
    val attribution: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ForwardGeocode

        if (type != other.type) return false
        if (!query.contentEquals(other.query)) return false
        if (!features.contentEquals(other.features)) return false
        if (attribution != other.attribution) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + query.contentHashCode()
        result = 31 * result + features.contentHashCode()
        result = 31 * result + attribution.hashCode()
        return result
    }

}

@Serializable
data class ForwardGeocodeFeature(
    val id: String,
    val type: String,
    @SerialName("place_type") val placeType: Array<String>,
    val relevance: Int,
    val properties: HashMap<String, String>,
    val text: String,
    @SerialName("place_name") val placeName: String,
    val bbox: Array<Double>,
    val center: Array<Double>,
    val geometry: ForwardGeocodeFeatureGeometry,
    val context: Array<ForwardGeocodeFeatureContext>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ForwardGeocodeFeature

        if (id != other.id) return false
        if (type != other.type) return false
        if (!placeType.contentEquals(other.placeType)) return false
        if (relevance != other.relevance) return false
        if (properties != other.properties) return false
        if (text != other.text) return false
        if (placeName != other.placeName) return false
        if (!bbox.contentEquals(other.bbox)) return false
        if (!center.contentEquals(other.center)) return false
        if (geometry != other.geometry) return false
        if (!context.contentEquals(other.context)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + placeType.contentHashCode()
        result = 31 * result + relevance
        result = 31 * result + properties.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + placeName.hashCode()
        result = 31 * result + bbox.contentHashCode()
        result = 31 * result + center.contentHashCode()
        result = 31 * result + geometry.hashCode()
        result = 31 * result + context.contentHashCode()
        return result
    }
}

@Serializable
data class ForwardGeocodeFeatureGeometry(
    val type: String,
    val coordinates: Array<Double>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ForwardGeocodeFeatureGeometry

        if (type != other.type) return false
        if (!coordinates.contentEquals(other.coordinates)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + coordinates.contentHashCode()
        return result
    }
}

@Serializable
data class ForwardGeocodeFeatureContext(
    val id: String,
    val wikidata: String,
    val text: String
)