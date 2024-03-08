package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class PatternMatchServiceImpl(configPath: String) : PatternMatchService {
    companion object {
        val L = LoggerFactory.getLogger(PatternMatchServiceImpl::class.java)!!
    }

    private data class Responses(val id: String, val responses: List<String>) {
        init {
            if (responses.isEmpty()) {
                throw IllegalArgumentException("Responses $id has no responses!")
            }
        }
    }

    private val config = Json.decodeFromString<Config>(this::class.java.getResource(configPath)!!.readText())

    private val lookup = HashMap<String, Responses>()

    init {
        L.info("Loading from configuration @ {}", configPath)
        // TODO: Handle regex - can these be combined into a single string?
        config.entries.forEach { entry ->
            val responses = Responses(entry.id, entry.responses.map { it.response }.toList())

            entry.patterns.forEach { pattern ->
                if (pattern.regex)
                    throw NotImplementedError("Regex entries aren't implemented")
                if (lookup.containsKey(pattern.pattern)) {
                    L.warn(
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
}