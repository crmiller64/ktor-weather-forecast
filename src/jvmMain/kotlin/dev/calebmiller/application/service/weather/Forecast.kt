package dev.calebmiller.application.service.weather

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Forecast(
    val properties: ForecastProperties
)

@Serializable
data class ForecastProperties(
    val periods: List<ForecastPeriod>
)

@Serializable
data class ForecastPeriod(
    val number: Int,
    val name: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val startTime: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val endTime: OffsetDateTime,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val temperatureTrend: String?,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String
)

object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        val string = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value)
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        val string = decoder.decodeString()
        return OffsetDateTime.parse(string)
    }
}
