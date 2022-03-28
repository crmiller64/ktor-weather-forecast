package dev.calebmiller.application

import dev.calebmiller.application.di.appModule
import dev.calebmiller.application.plugins.configureHTTP
import dev.calebmiller.application.plugins.configureRouting
import dev.calebmiller.application.plugins.configureSerialization
import io.ktor.application.*
import org.koin.core.module.Module
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(koinModules: List<Module> = listOf(appModule)) {
    configureHTTP()
    configureRouting()
    configureSerialization()

    install(Koin) {
        SLF4JLogger()
        modules(appModule)
    }
}