plugins {
    id("org.siouan.frontend-jdk11")
}

frontend {
    nodeVersion.set("17.6.0")
    assembleScript.set("run assemble")
    checkScript.set("run check")
    cleanScript.set("run clean")
}
