import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.Props
import react.dom.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.fc
import react.useState

private val scope = MainScope()

val app = fc<Props> {

    var (forecast, setForecast) = useState(emptyForecast())

    h1 { + "Weather Forecast" }

    child(coordinatesComponent) {
        attrs.onSubmit = { latitude, longitude ->
            scope.launch {
                setForecast(getWeatherForecast(latitude, longitude))
            }
        }
    }

    ul {
        forecast.properties.periods.sortedBy(ForecastPeriod::number).forEach { period ->
            li {
                key = "${period.number}-${period.name}"
                + period.toString()
            }
        }
    }
}