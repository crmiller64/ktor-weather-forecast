import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onSubmitFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

external interface CoordinatesProps : Props {
    var onSubmit: (Double, Double) -> Unit
}

external interface CoordinatesState: State {
    var latitude: String
    var longitude: String
}

private val initialState = object : CoordinatesState {
    override var latitude = ""
    override var longitude = ""
}

val coordinatesComponent = fc<CoordinatesProps> { props ->

    var coordinatesState by useState { initialState }

    val submitHandler: (Event) -> Unit = {
        it.preventDefault()
        if (isDouble(coordinatesState.latitude) && isDouble(coordinatesState.longitude)) {
            props.onSubmit(coordinatesState.latitude.toDouble(), coordinatesState.longitude.toDouble())
        }
    }

    form {
        attrs.onSubmitFunction = submitHandler

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

        span {
            +"Your coordinates are: ${coordinatesState.latitude}, ${coordinatesState.longitude}"
        }
    }
}
