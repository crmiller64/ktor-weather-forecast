import kotlinext.js.clone
import react.State

// Utility function taken from:
// https://dev.to/skalabledev/kotlinjs-and-multiple-state-hooks-part-two-3k69
internal inline fun <T : State> setState(
    oldState: T,
    newState: T.() -> Unit
): T = clone(oldState).apply(newState)

fun isDouble(value: String): Boolean = value.toDoubleOrNull() != null