import kotlinx.css.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.*

object CoordinatesStyles : StyleSheet("CoordinatesStyles") {
    val wrapper by css {

        display = Display.grid
        // use repeat() to define the grid has 2 columns
        gridTemplateColumns = GridTemplateColumns.repeat("2, max-content")
        gap = 5.px

        children("label") {
            textAlign = TextAlign.right
            after {
                content = QuotedString(":")
            }
        }
    }
}

external interface CoordinatesProps : Props {
    var onSubmit: (Double, Double) -> Unit
}

val coordinatesComponent = fc<CoordinatesProps> { props ->

    var (latitude, setLatitude) = useState(0.0)
    var (longitude, setLongitude) = useState(0.0)

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()

        setLatitude(0.0)
        setLongitude(0.0)

        props.onSubmit(latitude, longitude)
    }

    styledForm {
        attrs.onSubmitFunction = submitHandler
        css { +CoordinatesStyles.wrapper }

        label {
            + "Latitude"
            attrs {
                htmlFor = "latitude"
            }
        }
        input {
            attrs {
                type = InputType.number
                id = "latitude"
                name = "latitude"
                value = latitude.toString()
                max = "90"
                min = "-90"
                step = "0.001"
                onChangeFunction = { event ->
                    value = (event.target as HTMLInputElement).value
                    setLatitude(value.toDouble())
                }
            }
        }

        label {
            + "Longitude"
            attrs {
                htmlFor = "longitude"
            }
        }
        input {
            attrs {
                type = InputType.number
                id = "longitude"
                name = "longitude"
                value = longitude.toString()
                max = "180"
                min = "-180"
                step = "0.001"
                onChangeFunction = { event ->
                    value = (event.target as HTMLInputElement).value
                    setLongitude(value.toDouble())
                }
            }
        }

        styledInput {
            css {
                gridColumn = GridColumn("span 2")
            }
            attrs.type = InputType.submit
        }

        label {
            + "Your coordinates are"
            attrs {
                htmlFor = "coordinates"
            }
        }
        span {
            +"${latitude}, ${longitude}"
            attrs {
                id = "coordinates"
            }
        }
    }
}
