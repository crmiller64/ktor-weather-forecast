package dev.calebmiller.application.service.weather.api

import Forecast
import ForecastPeriod
import ForecastProperties
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/* Representation of forecast data received from the weather.gov API. */

@Serializable
data class WeatherForecast(
    val properties: WeatherForecastProperties
)

@Serializable
data class WeatherForecastProperties(
    val periods: List<WeatherForecastPeriod>
)

@Serializable
data class WeatherForecastPeriod(
    val number: Int,
    val name: String,
    @Serializable(OffsetDateTimeSerializer::class)
    val startTime: OffsetDateTime,
    @Serializable(OffsetDateTimeSerializer::class)
    val endTime: OffsetDateTime,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val temperatureTrend: String? = null,
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

// translate weather.gov response into our domain object
fun toForecast(weatherForecast: WeatherForecast): Forecast {
    val periods = weatherForecast.properties.periods.map { w ->
        // TODO may need to translate offsetdatetime to client timezone instant before setting to this object
        // i.e. offsetdatetime.sameInstantAtTimeZone(clientTimezone/clientUtcOffset)
        val startTime = LocalDateTime(
            w.startTime.year,
            w.startTime.month,
            w.startTime.dayOfMonth,
            w.startTime.hour,
            w.startTime.minute,
            w.startTime.second,
            w.startTime.nano
        )
        val endTime = LocalDateTime(
            w.endTime.year,
            w.endTime.month,
            w.endTime.dayOfMonth,
            w.endTime.hour,
            w.endTime.minute,
            w.endTime.second,
            w.endTime.nano
        )

        ForecastPeriod(
            w.number,
            w.name,
            startTime,
            UtcOffset(w.startTime.offset),
            endTime,
            UtcOffset(w.endTime.offset),
            w.isDaytime,
            w.temperature,
            w.temperatureUnit,
            w.temperatureTrend,
            w.windSpeed,
            w.windDirection,
            w.shortForecast,
            w.detailedForecast
        )
    }
    return Forecast(ForecastProperties(periods))
}