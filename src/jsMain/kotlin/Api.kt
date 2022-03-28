import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.browser.window

val endpoint = window.location.origin // only needed until https://github.com/ktorio/ktor/issues/1695 is resolved

val jsonClient = HttpClient {
    install(JsonFeature) {
        serializer = KotlinxSerializer()
    }
}

suspend fun getWeatherForecast(latitude: Double, longitude: Double): Forecast {
    val response: HttpResponse = jsonClient.get(
        endpoint + "/weather/forecast?latitude=${latitude}&longitude=${longitude}"
    )

    if (response.status != HttpStatusCode.OK) {
        val errorMessage: String = response.receive()
        throw Exception(errorMessage)
    }
    return response.receive()
}
