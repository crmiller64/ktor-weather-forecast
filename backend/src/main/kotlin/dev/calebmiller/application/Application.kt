package dev.calebmiller.application

import dev.calebmiller.application.config.setupConfig
import dev.calebmiller.application.di.appModule
import dev.calebmiller.application.plugins.configureHTTP
import dev.calebmiller.application.plugins.configureRouting
import dev.calebmiller.application.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.Netty
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    // Start Ktor
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {
    // Load modules that specify plugin configurations
    configureHTTP()
    configureRouting()
    configureSerialization()

    // Load Koin appModule for dependency injection (DI)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    // Execute configuration setup after app start
    setupConfig()
}