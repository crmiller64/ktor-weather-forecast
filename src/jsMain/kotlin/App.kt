import kotlinx.coroutines.MainScope
import react.Props
import react.dom.h1
import react.functionComponent

private val scope = MainScope()

val app = functionComponent<Props> {

    h1 { + "Weather Forecast" }

    child(Welcome::class) {
        attrs {
            name = "Kotlin/JS"
        }
    }
}