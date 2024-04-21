package dev.calebmiller.application.features.health.resource

import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Endpoint(s) for simple / quick health checks.
@Resource("/health")
class HealthEndpoint {

    @Resource("")
    class Health(val parent: HealthEndpoint = HealthEndpoint())
}

// ROUTE / ENDPOINT HANDLERS
fun Route.healthEndpoint() {
    get<HealthEndpoint.Health> { request ->
        try {
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError)
        }
    }
}