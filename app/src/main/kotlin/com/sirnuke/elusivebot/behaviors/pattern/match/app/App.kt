/**
 * Entrypoint for Pattern match service.
 */

package com.sirnuke.elusivebot.behaviors.pattern.match.app

import com.sirnuke.elusivebot.behaviors.pattern.match.lib.PatternMatchService
import com.sirnuke.elusivebot.schema.ChatMessage
import com.uchuhimo.konf.Config
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.apache.kafka.streams.kstream.KStream
import org.slf4j.LoggerFactory

import java.util.concurrent.atomic.AtomicBoolean

import kotlin.concurrent.thread
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Suppress("TOO_LONG_FUNCTION")
fun main() {
    val log = LoggerFactory.getLogger("com.sirnuke.elusivebot.behaviors.pattern.match.AppKt")!!
    log.info("Starting Pattern Match service")
    val config = Config { addSpec(PatternMatchSpec) }.from.env()

    log.info("Starting Pattern Match; producer {} & consumer {}", config[PatternMatchSpec.Kafka.producerTopic],
        config[PatternMatchSpec.Kafka.consumerTopic])

    val service = PatternMatchService.create()

    val running = AtomicBoolean(true)

    val consumerConfig = StreamsConfig(
        mapOf<String, Any>(
            StreamsConfig.APPLICATION_ID_CONFIG to config[PatternMatchSpec.serviceId],
            StreamsConfig.BOOTSTRAP_SERVERS_CONFIG to config[PatternMatchSpec.Kafka.bootstrap],
            StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG to Serdes.String().javaClass.name,
            StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG to Serdes.String().javaClass.name
        )
    )

    val builder = StreamsBuilder()

    val producerConfig = mapOf(
        "bootstrap.servers" to config[PatternMatchSpec.Kafka.bootstrap],
        "key.serializer" to "org.apache.kafka.common.serialization.StringSerializer",
        "value.serializer" to "org.apache.kafka.common.serialization.StringSerializer"
    )

    val producer: KafkaProducer<String, String> = KafkaProducer(producerConfig)

    val consumer: KStream<String, String> = builder.stream(config[PatternMatchSpec.Kafka.consumerTopic])

    consumer.foreach { key, raw ->
        log.info("Got input {} {}", key, raw)
        val message: ChatMessage = Json.decodeFromString(raw)
        service.process(message.message)?.let {
            log.info("Sending {} -> {}", message.message, it)
            val response = ChatMessage(header = message.header, user = null, message = it)
            producer.send(
                ProducerRecord(
                    config[PatternMatchSpec.Kafka.producerTopic], key, Json.encodeToString(response)
                )
            )
        }
    }

    val streams = KafkaStreams(builder.build(), consumerConfig)
    streams.start()

    Runtime.getRuntime().addShutdownHook(thread(start = false, name = "shutdown-hook") {
        running.set(false)
        streams.close()
        producer.close()
    })
}
