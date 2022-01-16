import kotlinx.html.classes
import kotlinx.html.role
import react.Props
import react.dom.attrs
import react.dom.div
import react.dom.span
import react.fc
import styled.styledDiv

val loaderComponent = fc<Props> {

    styledDiv {
        attrs {
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