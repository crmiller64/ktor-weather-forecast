import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.html.classes
import kotlinx.html.id
import kotlinx.html.role
import org.w3c.dom.HTMLElement
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

    div {
        attrs.classes += "container"

        h1 { + "Weather Forecast" }

        child(coordinatesComponent) {
            attrs.onSubmit = { latitude, longitude ->
                scope.launch {
                    displayLoader(true)
                    setForecast(getWeatherForecast(latitude, longitude))
                    displayLoader(false)
                }
            }
        }

        styledDiv {
            attrs {
                id = "loader"
                classes += "justify-content-center"
            }
            css.put("display", "none")

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

fun displayLoader(display: Boolean) {
    val value = if (display) "flex" else "none"
    (document.getElementById("loader") as HTMLElement).style.display = value
}