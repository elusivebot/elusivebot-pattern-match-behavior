/**
 * Entrypoint for Pattern match service.
 */

package com.sirnuke.elusivebot.behaviors.pattern.match.app

import com.sirnuke.elusivebot.behaviors.pattern.match.lib.PatternMatchService
import com.sirnuke.elusivebot.common.Kafka
import com.sirnuke.elusivebot.schema.ChatMessage
import com.uchuhimo.konf.Config
import org.slf4j.LoggerFactory

import java.util.concurrent.atomic.AtomicBoolean

import kotlin.concurrent.thread
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("TOO_LONG_FUNCTION")
fun main() = runBlocking {
    val log = LoggerFactory.getLogger("com.sirnuke.elusivebot.behaviors.pattern.match.AppKt")!!
    val config = Config { addSpec(PatternMatchSpec) }.from.env()
    val running = AtomicBoolean(true)

    log.info("Starting Pattern Match; producer {} & consumer {}", config[PatternMatchSpec.Kafka.producerTopic],
        config[PatternMatchSpec.Kafka.consumerTopic])

    val service = PatternMatchService.create()

    val kafka = Kafka.Builder(
        applicationId = config[PatternMatchSpec.serviceId],
        bootstrap = config[PatternMatchSpec.Kafka.bootstrap],
        scope = this,
    ).registerConsumer(config[PatternMatchSpec.Kafka.consumerTopic]) { producer, key, msg: ChatMessage ->
        log.info("Got input {} {}", key, msg)
        service.process(msg.message)?.let { answer ->
            log.info("Sending {} -> {}", msg.message, answer)
            val response = ChatMessage(header = msg.header, user = null, message = answer)
            producer.send(config[PatternMatchSpec.Kafka.producerTopic], key, Json.encodeToString(response)) { _, ex ->
                ex?.let {
                    log.error("Unable to send response to {}", key, it)
                } ?: log.info("Done sending response on {}", key)
            }
        }
    }.build()

    Runtime.getRuntime().addShutdownHook(thread(start = false, name = "shutdown-hook") {
        running.set(false)
        kafka.close()
    })

    while (running.get()) {
        delay(5.seconds)
    }
}
