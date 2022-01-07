import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    window.onload = {
        document.getElementById("root")?.let { element ->
            render(element) {
                child(app)
            }
        }
    }
}
