plugins {
    id("com.sirnuke.elusivebot.behaviors.pattern.match.kotlin-common-conventions")
    application
}

val kafkaApiVersion = "3.6.1"

dependencies {
    implementation("com.uchuhimo:konf:1.1.2")
    implementation("org.apache.kafka:kafka-streams:$kafkaApiVersion")
    implementation("org.apache.kafka:kafka-clients:$kafkaApiVersion")

    implementation("com.sirnuke.elusivebot:elusivebot-schema:0.1.0-SNAPSHOT")
    implementation("com.sirnuke.elusivebot:elusivebot-common:0.1.0-SNAPSHOT")

    testImplementation("org.apache.kafka:kafka-streams-test-utils:$kafkaApiVersion")
}
