plugins {
    id("com.sirnuke.elusivebot.behaviors.pattern.match.kotlin-library-conventions")
    kotlin("plugin.serialization") version "1.9.22"
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
}
