import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import react.Props
import react.dom.h1
import react.fc

private val scope = MainScope()

val app = fc<Props> {

    h1 { + "Weather Forecast" }

    child(coordinatesComponent) {
        attrs.onSubmit = { latitude, longitude ->
            scope.launch {
                // TODO get weather station forecast with given latitude and longitude
                println("coordinates received: [latitude=${latitude}, longitude=${longitude}]")
            }
        }
    }
}