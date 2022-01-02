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

external interface CoordinatesState: State {
    var latitude: String
    var longitude: String
}

private val initialState = object : CoordinatesState {
    override var latitude = "0"
    override var longitude = "0"
}

val coordinatesComponent = fc<CoordinatesProps> { props ->

    var coordinatesState by useState { initialState }

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        if (isDouble(coordinatesState.latitude) && isDouble(coordinatesState.longitude)) {
            props.onSubmit(coordinatesState.latitude.toDouble(), coordinatesState.longitude.toDouble())
        }
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
                value = coordinatesState.latitude
                onChangeFunction = { event ->
                    coordinatesState = setState(coordinatesState) {
                        latitude = (event.target as HTMLInputElement).value
                    }
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
                value = coordinatesState.longitude
                onChangeFunction = { event ->
                    coordinatesState = setState(coordinatesState) {
                        longitude = (event.target as HTMLInputElement).value
                    }
                }
            }
        }

        label {
            + "Your coordinates are"
            attrs {
                htmlFor = "coordinates"
            }
        }
        span {
            +"${coordinatesState.latitude}, ${coordinatesState.longitude}"
            attrs {
                id = "coordinates"
            }
        }
    }
}
