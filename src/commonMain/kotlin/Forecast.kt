import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val city: String,
    val state: String,
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
    val startTime: LocalDateTime,
    val startTimeUtcOffset: UtcOffset,
    val endTime: LocalDateTime,
    val endTimeUtcOffset: UtcOffset,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val temperatureTrend: String? = null,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String
)

fun emptyForecast() = Forecast("", "", ForecastProperties(emptyList()))
