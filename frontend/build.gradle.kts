plugins {
    id("org.siouan.frontend-jdk11")
}

// FRONTEND BUILD
frontend {
    nodeVersion.set("18.20.2")
    nodeInstallDirectory.set(file("${projectDir}/node"))
    packageJsonDirectory.set(file("$projectDir"))
    cacheDirectory.set(file("${projectDir}/.frontend-gradle-plugin"))
    assembleScript.set("run assemble")
    cleanScript.set("run clean")
    checkScript.set("run check")
    verboseModeEnabled.set(true)
}