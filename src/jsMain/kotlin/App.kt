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

    var (forecast, setForecast) = useState(Forecast())
    var (loading, setLoading) = useState(false)

    div {
        attrs.classes += "container"

        h1 { + "Weather Forecast" }

        child(coordinatesComponent) {
            attrs.onSubmit = { latitude, longitude ->
                setForecast(Forecast())
                setLoading(true)
                scope.launch {
                    try {
                        setForecast(getWeatherForecast(latitude, longitude))
                    } catch (e: Exception) {
                        // TODO show error alert with exception message
                    }
                    setLoading(false)
                }
            }
        }

        if (loading) {
            child(loaderComponent)
        }

        // TODO will switch user input from lat+lon to city+state
        // if (forecast.city.isNotBlank() && forecast.state.isNotBlank()) {
        //     h2 { + "Forecast for ${forecast.city}, ${forecast.state}" }
        // }

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