package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import org.slf4j.LoggerFactory

import kotlinx.serialization.json.Json

/**
 * Concrete implementation of the Pattern Match service.
 *
 * @param configPath
 */
class PatternMatchServiceImpl(configPath: String) : PatternMatchService {
    private val config: Config = Json.decodeFromString(this::class.java.getResource(configPath)!!.readText())
    private val lookup: HashMap<String, Responses> = HashMap()

    init {
        log.info("Loading from configuration @ {}", configPath)
        // TODO: Handle regex - can these be combined into a single string?
        config.entries.forEach { entry ->
            val responses = Responses(entry.id, entry.responses.map { it.response }.toList())

            entry.patterns.forEach { pattern ->
                if (pattern.regex) {
                    throw NotImplementedError("Regex entries aren't implemented")
                }
                if (lookup.containsKey(pattern.pattern)) {
                    log.warn(
                        "Replacing existing pattern {} from {} with one from {}",
                        pattern.pattern,
                        lookup[pattern.pattern]!!.id,
                        entry.id
                    )
                }
                lookup[pattern.pattern] = responses
            }
        }
    }

    override fun process(input: String): String? {
        val responses = lookup[input] ?: return null
        return responses.responses.random()
    }

    /**
     * @property id
     * @property responses
     */
    private data class Responses(val id: String, val responses: List<String>) {
        init {
            if (responses.isEmpty()) {
                throw IllegalArgumentException("Responses $id has no responses!")
            }
        }
    }
    companion object {
        val log = LoggerFactory.getLogger(PatternMatchServiceImpl::class.java)!!
    }
}
