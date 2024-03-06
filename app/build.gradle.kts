plugins {
    id("com.sirnuke.elusivebot.behaviors.pattern.match.kotlin-application-conventions")
}

dependencies {
    implementation(project(":lib"))
}

application {
    mainClass.set("com.sirnuke.elusivebot.behaviors.pattern.match.app.AppKt")
}
