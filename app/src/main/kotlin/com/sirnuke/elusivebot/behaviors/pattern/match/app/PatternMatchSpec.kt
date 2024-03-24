package com.sirnuke.elusivebot.behaviors.pattern.match.app

import com.uchuhimo.konf.ConfigSpec

object PatternMatchSpec : ConfigSpec() {
    val serviceId by optional("Pattern-Match")

    object Kafka : ConfigSpec() {
        val bootstrap by required<String>()
        val producerTopic by optional("pattern-match-input")
        val consumerTopic by optional("pattern-match-output")
    }
}
