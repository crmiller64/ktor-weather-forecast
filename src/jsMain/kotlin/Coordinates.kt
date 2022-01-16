import kotlinx.css.QuotedString
import kotlinx.css.TextAlign
import kotlinx.css.content
import kotlinx.css.textAlign
import kotlinx.html.InputType
import kotlinx.html.classes
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
        props.onSubmit(latitude, longitude)
    }

    styledForm {
        css { +CoordinatesStyles.wrapper }
        attrs {
            onSubmitFunction = submitHandler
            attrs.classes += "mb-3"
        }

        div {
            attrs.classes = setOf("row", "mb-3")

            label {
                + "Latitude"
                attrs {
                    classes = setOf("col-sm-1", "col-form-label")
                    htmlFor = "latitude"
                }
            }
            div {
                attrs.classes += "col-sm-2"

                input {
                    attrs {
                        classes += "form-control"
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
            }
            div {
                attrs.classes += "col-auto"

                span {
                    attrs.classes += "form-text"
                    + " Must be a number between 90 and -90, and up to 3 decimal places."
                }
            }
        }

        div {
            attrs.classes = setOf("row", "mb-3")

            label {
                + "Longitude"
                attrs {
                    classes = setOf("col-sm-1", "col-form-label")
                    htmlFor = "longitude"
                }
            }
            div {
                attrs.classes += "col-sm-2"

                input {
                    attrs {
                        classes += "form-control"
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
            }
            div {
                attrs.classes += "col-auto"

                span {
                    attrs.classes += "form-text"
                    + " Must be a number between 180 and -180, and up to 3 decimal places."
                }
            }
        }

        button {
            attrs.classes = setOf("btn", "btn-primary")
            + "Submit"
        }


        div {
            attrs.classes = setOf("row", "mb-3")

            div {
                attrs.classes += "col-auto"

                span {
                    attrs.classes += "form-text"
                    + "Your coordinates are: ${latitude}, ${longitude}"
                }
            }
        }

    }
}
