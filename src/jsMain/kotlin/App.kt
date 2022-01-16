import io.ktor.client.features.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.role
import react.Props
import react.dom.attrs
import react.dom.div
import react.dom.h1
import react.dom.html.ReactHTML.li
import react.dom.html.ReactHTML.ul
import react.dom.span
import react.fc
import react.useState
import styled.styledDiv

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
                    } catch (e: ServerResponseException) {
                        // TODO
                    }
                    setLoading(false)
                }
            }
        }

        if (loading) {
            styledDiv {
                attrs {
                    id = "loader"
                    classes = setOf("d-flex", "justify-content-center")
                }

                div {
                    attrs {
                        classes += "spinner-border"
                        role = "status"
                    }

                    span {
                        attrs.classes += "visually-hidden"
                        + "Loading..."
                    }
                }
            }
        }

        ul {
            forecast.properties?.periods?.sortedBy(ForecastPeriod::number)?.forEach { period ->
                li {
                    key = "${period.number}-${period.name}"
                    + period.toString()
                }
            }
        }
    }
}