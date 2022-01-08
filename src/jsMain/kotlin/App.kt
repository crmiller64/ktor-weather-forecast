import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.id
import react.Props
import react.dom.attrs
import react.dom.div
import react.dom.h1
import react.fc
import react.useState

private val scope = MainScope()

val app = fc<Props> {

    var (forecast, setForecast) = useState("")

    h1 { + "Weather Forecast" }

    child(coordinatesComponent) {
        attrs.onSubmit = { latitude, longitude ->
            scope.launch {
                setForecast(getWeatherForecast(latitude, longitude).toString())
            }
        }
    }

    div {
        + forecast
        attrs {
            id = "forecast"
        }
    }
}