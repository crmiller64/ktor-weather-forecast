import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val coroutines_version: String by project
val kotlin_datetime_version: String by project
val koin_version: String by project
val koin_test_version: String by project
val kotlin_logging_version: String by project
// command line argument
val skipFrontend: String by project

plugins {
    application
    id("org.siouan.frontend-jdk11")
    id("io.ktor.plugin") version "2.3.7"

    kotlin("jvm") version "1.9.21"
    kotlin("plugin.serialization") version "1.9.21"
}

group = "dev.calebmiller"
version = "1.1.0"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-compression:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")

    implementation("io.ktor:ktor-client-cio:$ktor_version")
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-client-resources:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlin_datetime_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.github.microutils:kotlin-logging-jvm:$kotlin_logging_version")

    implementation("io.insert-koin:koin-core:$koin_version")
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")

    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("io.ktor:ktor-client-mock:$ktor_version")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutines_version")

    testImplementation("io.insert-koin:koin-test:$koin_version")
    testImplementation("io.insert-koin:koin-test-junit5:$koin_version")

    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.create("stage") {
    dependsOn("installDist")
}

// FRONTEND BUILD
tasks.register<Copy>("processFrontendResources") {
    // Directory containing the artifacts in the frontend project
    val frontendBuildDir = file("${project(":frontend").buildDir}")
    /*
     * Directory where the frontend artifacts must be copied to be packaged altogether with the backend
     * by the 'war' plugin.
     */
    val frontendResourcesDir = file("${project.buildDir}/resources/main/public")

    group = "Frontend"
    description = "Process frontend resources"
    dependsOn(project(":frontend").tasks.named("assembleFrontend"))

    from(frontendBuildDir)
    into(frontendResourcesDir)
}

tasks.named("processResources") {
    // skip frontend build if directed
    if (!project.hasProperty("skipFrontend")) {
        dependsOn("processFrontendResources")
    }
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "11"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "11"
}