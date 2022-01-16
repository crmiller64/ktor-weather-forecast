import io.ktor.client.features.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.classes
import react.Props
import react.dom.div
import react.dom.h1
import react.dom.h2
import react.dom.li
import react.dom.ul
import react.fc
import react.useState

private val scope = MainScope()

val app = fc<Props> {

    var (forecast, setForecast) = useState(emptyForecast())
    var (loading, setLoading) = useState(false)

    div {
        attrs.classes += "container"

        h1 { + "Weather Forecast" }

        child(coordinatesComponent) {
            attrs.onSubmit = { latitude, longitude ->
                setLoading(true)
                scope.launch {
                    try {
                        setForecast(getWeatherForecast(latitude, longitude))
                    } catch (e: ResponseException) {
                        // TODO
                    }
                    setLoading(false)
                }
            }
        }

        if (loading) {
            child(loaderComponent)
        }

        if (forecast.city.isNotBlank() && forecast.state.isNotBlank()) {
            h2 { + "Forecast for ${forecast.city}, ${forecast.state}" }
        }

        div {
            attrs.classes = setOf("row")

            ul {
                forecast.properties.periods.sortedBy(ForecastPeriod::number).forEach { period ->
                    li {
                        key = "${period.number}-${period.name}"
                        + period.toString()
                    }
                }
            }
        }

    }
}