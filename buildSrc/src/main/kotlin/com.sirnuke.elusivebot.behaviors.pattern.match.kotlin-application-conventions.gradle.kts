plugins {
    id("com.sirnuke.elusivebot.behaviors.pattern.match.kotlin-common-conventions")
    application
}

dependencies {
    implementation("com.uchuhimo:konf:1.1.2")

    implementation("com.sirnuke.elusivebot:elusivebot-schema:0.1.0-SNAPSHOT")
    implementation("com.sirnuke.elusivebot:elusivebot-common:0.1.0-SNAPSHOT")
}
