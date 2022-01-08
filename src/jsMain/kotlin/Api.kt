import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import kotlinx.browser.window
import kotlinx.serialization.json.JsonObject

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}

suspend fun getWeatherForecast(latitude: Double, longitude: Double): JsonObject {
    return jsonClient.get(endpoint + "/weather/forecast?latitude=${latitude}&longitude=${longitude}")
}
