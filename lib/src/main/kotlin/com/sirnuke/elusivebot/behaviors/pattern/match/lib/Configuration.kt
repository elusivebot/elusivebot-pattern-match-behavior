/**
 * Data classes for loading pattern match JSON configuration.
 */

package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import kotlinx.serialization.Serializable

/**
 * Root POJO for the pattern match library configuration.
 *
 * @property entries List of pattern configurations.
 */
@Serializable
data class Config(val entries: List<Entry>)

/**
 * A single pattern match entry, containing one or more patterns and one or more responses.
 *
 * @property id Unique identifier for this entry.
 * @property patterns List of patterns.
 * @property responses List of responses.
 */
@Serializable
data class Entry(
    val id: String,
    val patterns: List<Pattern>,
    val responses: List<Response>
)

/**
 * Input pattern configuration.
 *
 * @property pattern The raw or regex string to compare against input strings.
 * @property regex True if this entry is regex, raw lookup otherwise.
 */
@Serializable
data class Pattern(val pattern: String, val regex: Boolean = false)

/**
 * Output response configuration.
 *
 * @property response The response to return if this entry matches the input.
 */
@Serializable
data class Response(val response: String)
