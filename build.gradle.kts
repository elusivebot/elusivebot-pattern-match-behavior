plugins {
    id("net.researchgate.release") version "3.0.2"
}

release {
    tagTemplate = "v\${version}"
}

tasks.register("printVersion") {
    doLast {
        println(project.version)
    }
}