package com.sirnuke.elusivebot.behaviors.pattern.match.lib

import kotlinx.serialization.Serializable

@Serializable
data class Config(val entries: List<Entry>)

@Serializable
data class Entry(val id: String, val patterns: List<Pattern>, val responses: List<Response>)

@Serializable
data class Pattern(val pattern: String, val regex: Boolean = false)

@Serializable
data class Response(val response: String)
