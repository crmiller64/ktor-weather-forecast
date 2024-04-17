pluginManagement {
    plugins {
        id("org.siouan.frontend-jdk11") version "8.0.0"
    }
}

rootProject.name = "ktor-weather-forecast"
include("backend", "frontend")